<?php

namespace App\Form;

use App\Entity\CategEvent;
use App\Entity\Sousservice;
use App\Entity\Service;
use App\Entity\Personne;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
class SousserviceType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('image')
            ->add('description')
            ->add('prix')
            ->add('note')
            ->add('idEventcateg',EntityType::class,['class'=> CategEvent::class,'choice_label'=>'type','multiple'=>false,'expanded'=>false])
            ->add('idPers',EntityType::class,['class'=> Personne::class,'choice_label'=>'nomPers','multiple'=>false,'expanded'=>false])
            ->add('idService',EntityType::class,['class'=> Service::class,'choice_label'=>'nom','multiple'=>false,'expanded'=>false])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Sousservice::class,
        ]);
    }
}
