<?php

namespace App\Form;

use App\Entity\Personne;
use App\Entity\Reclamation;
use App\Entity\Reponse;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReponseModifierType extends AbstractType
{
    private $entityManager;
    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $sender = $this->entityManager->getRepository(Personne::class)->findBy(['idPers' => (int) $options['sender']]);
        $rec = $this->entityManager->getRepository(Reclamation::class)->findBy(['idRec' => (int) $options['rec']]);
        $fakeOne = [];
        $fakeOne['one'] = $sender;
        $fakeTwo['two'] = $rec;
        $builder
            ->add('message',TextType::class,[
                'attr' => [
                    'class' => 'form-control form-text mb-2'
                ],
                'data' => $options['mess'],
                'empty_data' => $options['mess'],
                'label' => false
            ])
            ->add('timestamp',DateTimeType::class,[
                'attr' => [
                    'hidden' => true
                ],
                'label' => false,
                'data' => new \DateTime()
            ])
            ->add('isedited',HiddenType::class,[
                'empty_data' => 1,
                'data' => 1,
            ])
            ->add('rec',ChoiceType::class,[
                'choices' => $fakeTwo,
                'attr' => ['hidden' => true],
                'label' => false,
                'data' => $rec
            ])
            ->add('senderid',ChoiceType::class,[
                'choices' => $fakeOne,
                'attr' => ['hidden' => true],
                'label' => false,
                'data' => $sender
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reponse::class,
            'attr' => ['class' => 'd-flex flex-column justify-content-center'],
            'mess' => '',
            'sender' => '',
            'rec' => '',
        ]);
    }
}
