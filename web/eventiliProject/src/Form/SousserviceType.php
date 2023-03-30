<?php

namespace App\Form;

use App\Entity\CategEvent;
use App\Entity\Sousservice;
use App\Entity\Service;
use App\Entity\Personne;
use App\Repository\CategEventRepository;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Doctrine\ORM\EntityManagerInterface;
class SousserviceType extends AbstractType
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        
        $builder
            ->add('nom')
            ->add('image',FileType::class,[
                'attr'=>[
                    'class' => 'form-control',
                    
                    'placeholder' => 'aucune image'
                ]
                ,
                'data_class' => null,
            ])
            ->add('description',TextareaType::class,[
                'attr'=>[
                    'class' => 'form-control',
                    'placeholder' => 'Description'
                ]
            ])
            ->add('prix')
            // ->add('note')
            // ->add('idEventcateg',EntityType::class,['class'=> CategEvent::class,'choice_label'=>'type','multiple'=>false,'expanded'=>false])
            // ->add('idEventcateg', ChoiceType::class, [
            //     'class' => CategEvent::class,
            //     'choice_label' => 'type',
            //     'multiple' => false,
            //     'expanded' => true,
            //     'query_builder' => function (CategEventRepository $er) {
            //         return $er->createQueryBuilder('c')
            //             ->orderBy('c.type', 'ASC');
            //     },
            // ])
            // ->add('idEventcateg', ChoiceType::class, [
            //     'choices' => $this->entityManager->getRepository(CategEvent::class)->findListCateg(),
            //     'choice_label' => 'type',
            //     'multiple' => true,
            //     'expanded' => true,
            //     'attr' => ['class' => 'form-control'],
            // ])
            // ->add('idPers',EntityType::class,['class'=> Personne::class,'choice_label'=>'nomPers','multiple'=>false,'expanded'=>false])
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
