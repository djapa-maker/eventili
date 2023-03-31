<?php

namespace App\Form;

use App\Entity\Imagess;
use App\Entity\Sousservice;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
class ImagessType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('img',FileType::class,[
            'attr'=>[
                'class' => 'form-control',
                
                'placeholder' => 'aucune image'
            ]
            ,
            'data_class' => null,
        ])
            // ->add('sousService',EntityType::class,['class'=> Sousservice::class,'choice_label'=>'nom','multiple'=>false,'expanded'=>false])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Imagess::class,
        ]);
    }
}
