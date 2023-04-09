<?php

namespace App\Form;

use App\Entity\Imgev;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ImgevType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            // ->add('imagee')
            // ->add('idEven')
            ->add('img',FileType::class,[
                'attr'=>[
                    'class' => 'form-control',
                    'placeholder' => 'aucune image',
                    'multiple' =>true,
                ]
                ,
                'data_class' => null,
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Imgev::class,
        ]);
    }
}
