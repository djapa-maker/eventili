<?php

namespace App\Form;
use App\Entity\Transaction;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextType as TypeTextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

use App\Entity\Sponsoring;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\NotNull;
use Symfony\Component\Validator\Constraints\GreaterThan;

class SponsoringType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('date_debut', DateType::class, [ 
             'constraints' => [
                new NotBlank(),
                new NotNull(),
                new GreaterThan(['value' => 'today', 'message' => 'La date de début doit être supérieure à aujourd\'hui.']),
            ],
        ])
        ->add('date_fin', DateType::class, [ 
             'constraints' => [
                new NotBlank(),
                new NotNull(),
                new GreaterThan(['propertyPath' => 'parent.all[date_debut].data', 'message' => 'La date de fin doit être supérieure à la date de début.']),
            ],
        ])
            ->add('nombre_impression', TypeTextType::class, [
                'attr'=>[
                    'class' => 'form-control',
                    'placeholder' => 'veuillez saisir votre nom'
                ],
                'constraints' => [
                    new NotBlank(),
                    new NotNull(),
                ],
            ])
            ->add('id_trans',EntityType::class,['class'=> Transaction::class,'choice_label'=>'id_trans','multiple'=>false,'expanded'=>false])
 
            ->add('id_event')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Sponsoring::class,
        ]);
    }
}
