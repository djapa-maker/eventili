<?php

namespace App\Form;

use App\Entity\Personne;
use App\Entity\Reclamation;
use App\Repository\PersonneRepository;
use App\Repository\ReclamationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Karser\Recaptcha3Bundle\Form\Recaptcha3Type;
use Karser\Recaptcha3Bundle\Validator\Constraints\Recaptcha3;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReclamationAjouterType extends AbstractType
{
    private $entityManager;
    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $user = $this->entityManager->getRepository(Personne::class)->findBy(['idPers' => $options['sender']]);
        $fakechoices = [];
        $fakechoices['1'] = $user;
        $builder
            ->add('titre',TextType::class,[
                'attr' => ['placeholder' => 'Le Sujet de votre Réclamation', 'class' => 'control input mb-2', 'style' => 'width: 120%;' ],
                'label' => false,
                'required' => true,
            ])
            ->add('description',TextareaType::class,[
                'attr' => [
                    'class' => 'control textarea mb-2',
                    'placeholder' => 'Veuillez détailler le probleme rencontré ici',
                    'style' => 'width: 120%;'
                ],
                'required' => true,
                'label'=>false,
            ])
            ->add('status',HiddenType::class,[
                'data' => 'ouvert'
            ])
            ->add('dateheure',DateTimeType::class,[
                'attr' =>['hidden' => 'true'],
                'label' => false,
                'data' => new \DateTime()
            ])
            ->add('userid',ChoiceType::class,[
                'choices' => $fakechoices,
                'attr' =>['hidden' => 'true'],
                'label' => false,
                'data' => $user
            ])
            ->add('captcha',Recaptcha3Type::class, [
                'constraints' => new Recaptcha3(),
                'action_name' => 'ajouter',
                'locale' => 'en',
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
            'sender' => '',
            'attr' => ['class' => 'd-flex flex-column justify-content-center align-items-center']
        ]);
    }
}
