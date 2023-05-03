<?php

namespace App\Form;

use App\Entity\Transaction;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextType as TypeTextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\NotNull;

class TransactionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('valeur_trans', TypeTextType::class, [
                'attr'=>[
                    'class' => 'form-control',
                    'placeholder' => 'veuillez saisir votre nom'
                ],
                'constraints' => [
                    new NotBlank(),
                    new NotNull(),
                ],
            ])
            ->add('devis', ChoiceType::class, [
                'choices' => [
                    'USD' => 'USD',
                    'EUR' => 'EUR',
                    'JPY' => 'JPY',
                    'GBP' => 'GBP',
                    'CHF' => 'CHF',
                    'CAD' => 'CAD',
                    'AUD' => 'AUD',
                    'NZD' => 'NZD',
                ],
            ])
            ->add('date_trans')
            ->add('mode_trans', ChoiceType::class, [
                'choices' => [
                    'Square' => 'Square',
                    'Stripe' => 'Stripe',
                    'Google Pay' => 'Google Pay',
                    'Amazon Pay' => 'Amazon Pay',
                ],
            ])
            ->add('montant_tot')
            ->add('user_id')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Transaction::class,
        ]);
    }
}
?>