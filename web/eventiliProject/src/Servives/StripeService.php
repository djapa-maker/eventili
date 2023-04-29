<?php

namespace App\Service;

use Stripe\Stripe;

class StripeService
{
    private $stripeSecretKey;

    public function __construct(string $stripeSecretKey)
    {
        $this->stripeSecretKey = $stripeSecretKey;
        Stripe::setApiKey($stripeSecretKey);
    }

    public function createCharge(int $amount, string $currency, string $description, string $source)
    {
        $charge = \Stripe\Charge::create([
            'amount' => $amount,
            'currency' => $currency,
            'description' => $description,
            'source' => $source,
        ]);

        return $charge;
    }
}
