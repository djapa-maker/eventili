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
use Symfony\Component\Form\Extension\Core\Type\NumberType;
//---------------------------------------------------------------------------------------
class AvisType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('rating',NumberType::class)
            ->add('comment')
            
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
