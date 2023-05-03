<?php

namespace App\Form;

use App\Entity\Personne;
use App\Entity\Reclamation;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;

class ReclamationType extends AbstractType
{
    private $entityManager;
    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $myEntities = $this->entityManager->getRepository(Personne::class)->findAll();
        $choices = [];

        foreach ($myEntities as $myEntity) {
            $choices[$myEntity->getNomPers().' '.$myEntity->getPrenomPers()] = $myEntity;
        }
        $status = [];
        $status['ouvert'] = "ouvert";
        $status['En Attente Admin'] = "EnAttenteRepAdmin";
        $status['En Attente User'] = "EnAttenteRepUser";
        $status['cloturer'] = "cloturer";
        $builder
            ->add('description',TextType::class,[
                'empty_data' => $options['desc'],
                'label' => false
            ])
            ->add('titre',TextType::class,[
                'label' => false,
            ])
            ->add('status',ChoiceType::class,[
                'choices' => $status,
                'label' => false
            ])
            ->add('dateheure',DateTimeType::class, [
                'html5' => true,
                'input' => 'datetime',
                'widget' => 'choice',
                'label' => false
            ])
            ->add('userid',ChoiceType::class,[
                'choices' => $choices,
                'label' => false
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
            'desc' => ''
        ]);
    }
}
