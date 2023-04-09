<?php

namespace App\Form;

use App\Entity\CategEvent;
use App\Entity\Evenement;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class EvenementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre')
            //->add('dateDebut')
            //->add('dateFin')
            ->add('descriptionEv',TextareaType::class,[
                'attr'=>[
                    'class' => 'form-control',
                    'placeholder' => 'Description'
                ]
            ])
            //->add('visibilite')
            ->add('limiteparticipant')
            ->add('prix')
            ->add('idCateg',EntityType::class,['class'=> CategEvent::class,'choice_label'=>'type','multiple'=>false,'expanded'=>false])
            // ->add('idPers')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Evenement::class,
        ]);
    }
}
