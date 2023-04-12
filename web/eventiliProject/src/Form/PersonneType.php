<?php

namespace App\Form;

use App\Entity\Personne;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType as TypeTextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\HttpFoundation\Request;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\MonFormulaireType;

class PersonneType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomPers')
            ->add('prenomPers',TypeTextType::class,[
                'attr'=>[
                    'placeholder' => 'veuillez saisir votre prénom'
                ]
            ])
            ->add('numTel',TypeTextType::class,[
                'attr'=>[
                    'placeholder' => 'veuillez saisir votre numéro de téléphone'
                ]
            ])
            ->add('email',TypeTextType::class,[
                'attr'=>[
                    'placeholder' => 'veuillez saisir votre adresse e-mail'
                ]
            ])
            ->add('mdp',TypeTextType::class,[
             
                'attr'=>[
                    'placeholder' => 'veuillez saisir un mot de passe'
                ]
               
            ])
            ->add('adresse',TypeTextType::class,[
                'attr'=>[
                    'placeholder' => 'veuillez saisir votre adresse'
                ]
            ])
            ->add('rib',TypeTextType::class,[
                'attr'=>[
                    'placeholder' => 'veuillez saisir votre rib'
                ]
            ])
            ->add('role', ChoiceType::class, [
                'choices' => [
                    'Organisateur' => 'organisateur',
                    'Partenaire' => 'partenaire',
                ],
            ])
           
         
        ;
    }
   
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Personne::class,
        ]);
    }
}
