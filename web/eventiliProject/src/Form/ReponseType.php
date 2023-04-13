<?php

namespace App\Form;

use App\Entity\Personne;
use App\Entity\Reclamation;
use App\Entity\Reponse;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReponseType extends AbstractType
{
    private $entityManager;
    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $idRec = $options['rec'];
        $myEntity = $this->entityManager->getRepository(Reclamation::class)->findOneBy(['idRec' => (int) $idRec]);
        $sender = $this->entityManager->getRepository(Personne::class)->findOneBy(['idPers' => (int) $options['sender']]);
        $rec = [];
        $rec[$myEntity->getIdRec()] = $myEntity;
        $pers = [];
        $pers[$sender->getIdPers()] = $sender;

        $builder
            ->add('message',TextType::class,
            ['attr' => ['class' => 'form-control form-text flex-grow-1'],'label' => false])
            ->add('timestamp',DateTimeType::class,[
                'attr' => ['hidden' => true],
                'label' => false,
                'data' => new \DateTime()
                ],
            )
            ->add('isedited',HiddenType::class,[
                'empty_data' => 0,
                'data' => 0,
                'attr' => ['hidden' => true]
            ])
            ->add('rec',ChoiceType::class,
            [
                'choices' => $rec,
                'attr' => ['hidden' => true],
                'label' => false,
                'data' => $myEntity

            ])
            ->add('senderid',ChoiceType::class,
            [
               'choices' => $pers,
                'attr' => ['hidden' => true],
                'label' => false,
                'data' => $sender
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reponse::class,
            'sender' => '',
            'rec' => '',
            'attr' => ['class' => 'd-flex flex-row gap-1']
        ]);
    }
}
