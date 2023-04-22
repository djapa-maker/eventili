<?php

namespace App\Form;
//---------------------------------------------------------------------------------------
use App\Entity\Avis;
use App\Entity\Personne;
use App\Entity\Sousservice;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
//---------------------------------------------------------------------------------------
class AvisType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('rating')
            ->add('comment')
            ->add('date')
            ->add('pers',EntityType::class,['class'=> Personne::class,'choice_label'=>'nomPers','multiple'=>false,'expanded'=>false])
            ->add('idService',EntityType::class,['class'=> Sousservice::class,'choice_label'=>'nom','multiple'=>false,'expanded'=>false])
        ;
    }
//---------------------------------------------------------------------------------------
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Avis::class,
        ]);
    }
}
