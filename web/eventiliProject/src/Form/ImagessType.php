<?php

namespace App\Form;

use App\Entity\Imagess;
use App\Entity\Sousservice;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
class ImagessType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('img')
            ->add('sousService',EntityType::class,['class'=> Sousservice::class,'choice_label'=>'nom','multiple'=>false,'expanded'=>false])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Imagess::class,
        ]);
    }
}
