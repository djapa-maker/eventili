<?php

namespace App\Form;
use Symfony\Component\HttpFoundation\File\File;
use App\Entity\Imagepers;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\Personne;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\DataTransformerInterface;
use Symfony\Component\Form\Exception\TransformationFailedException;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\OptionsResolver\OptionsResolver;


class ImagepersType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
           ->add('imagep')
            ->add('last')
           ->add('idPers',EntityType::class,['class'=> Personne::class,'choice_label'=>'idPers','multiple'=>false,'expanded'=>false])
           
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Imagepers::class,
        ]);
    }
}
