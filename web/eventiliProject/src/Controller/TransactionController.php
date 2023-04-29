<?php

namespace App\Controller;
use App\Entity\Transaction;
use App\Entity\Personne;

use App\Form\TransactionType;
use App\Repository\TransactionRepository;
use DateTimeZone;
 use Doctrine\ORM\EntityManagerInterface;
use Dompdf\Dompdf;
 use Knp\Component\Pager\PaginatorInterface;
use Stripe\Charge;
 use Stripe\Customer;
 use Stripe\Exception\ApiErrorException;
use Stripe\Exception\CardException;
use Stripe\Price;
 use Stripe\Product;
 use Stripe\Stripe;
 use Stripe\StripeClient;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
 use Symfony\Component\HttpFoundation\Request;
 use Symfony\Component\HttpFoundation\Response;
 use Symfony\Component\Routing\Annotation\Route;
 use MercurySeries\FlashyBundle\FlashyNotifier;
 
 use Twilio\Rest\Client;
 #[Route('/transaction')]
class TransactionController extends AbstractController
{  
    
    #[Route('/tpdf', name: 'pdf', methods: ['GET']) ]
    public function exportTransactionPdf($transaction, $request, FlashyNotifier $flashy): Response
    {
        $html = '<div style="text-align: center;">
            <h1 style="font-size: 24px; margin-bottom: 20px;">ARTMART</h1>
            <p><strong>Transaction ID:</strong> '.$transaction->getIdTrans().'</p>
            <p><strong>valeur transaction:</strong> '.$transaction->getValeurTrans().'</p>
            <p><strong>Currency:</strong> '.$transaction->getDevis().'</p>
            <p><strong>Transaction Date:</strong> '.$transaction->getDateTrans()->format('Y-m-d').'</p>
            <p><strong>mode transaction:</strong> '.$transaction->getModeTrans().'</p>
            <hr style="margin: 20px 0;">
            <p style="text-align: right;"><strong>user name:</strong> '.$transaction->getUserId()->getNomPers().' DT</p>
            <p style="text-align: right;"><strong>Total ammount:</strong> '.$transaction->getMontantTot().' DT</p>
            <hr style="margin: 20px 0;">
            <p>Thank you for shopping at eventili!</p>
            <p>Please contact us if you have any questions or concerns.</p>
            <p>Phone: (216) 28-899-807<br>Email: support@eventili.com<br>Website: www.eventili.tn</p>
        </div>';
        
        $dompdf = new Dompdf();
        $dompdf->loadHtml($html);
        $dompdf->setPaper('A4', 'portrait');
        $dompdf->render();
        $pdfContent = $dompdf->output();
    
        $filename = 'customer-transaction.pdf';
        $pdfPath = $request->server->get('DOCUMENT_ROOT') . '/' . $filename;
        file_put_contents($pdfPath, $pdfContent);
        
        // add notification
        $flashy->success('PDF file downloaded successfully!');
    
        return $this->redirectToRoute('app_transaction_newtrans');
    }
    
    #[Route('/successpayement', name: 'suc_payment', methods: ['GET'])]
    public function success(Request $request, EntityManagerInterface $entityManager,FlashyNotifier $flashy): Response
    {
        // Send a WhatsApp message using Twilio
        $sid    = "ACd1bfdea7342078edf93113625738d7b7";
        $token  = "a6d52d023d784964104b0165e7df47ac";
        $twilio = new Client($sid, $token);
        $message = $twilio->messages->create("whatsapp:+21628899807", [
            "from" => "whatsapp:+14155238886",
            "body" => "Payment successful"
        ]);
        $message = $twilio->messages->create("+21628899807", [
            "from" => "+14066307511",
            "body" => "Payment successful"
        ]);
        print($message->sid);
    
        // Create a new transaction
        $transaction = new Transaction();
        $transaction->setValeurTrans(100.00);
        $transaction->setDevis('USD');
        $transaction->setDateTrans(new \DateTime('now', new DateTimeZone('America/New_York')));
        $transaction->setModeTrans('Stripe');
        $transaction->setMontantTot(120.00);
    
        // Set the user ID for the transaction
        $user = $entityManager->getRepository(Personne::class)->find(34);
        $transaction->setUserId($user);
    
        // Save the transaction to the database
        $entityManager->persist($transaction);
        $entityManager->flush();
    
        // Generate the PDF file and return it as the response
        $pdf = $this->exportTransactionPdf($transaction, $request, $flashy);
        return $pdf;
    }
    

    
    #[Route('/fail', name: 'fail_payment')]
    public function fail(Request $request,EntityManagerInterface $entityManager): Response
    {
        header('Location: http://localhost/fail.html');
        exit;
    }
    #[Route('/payment', name: 'payment', methods: ['GET', 'POST'])]
    public function handlePayment(Request $request,EntityManagerInterface $entityManager): Response
    {
 

        $totalPrice = 0;
  $totalPrice = 100*100;
        $stripeSecretKey = "sk_test_51MiCB1Gqpa2PZgAVrlv2LKXgwScv6giwP2nPMkB6pTxddtrFvf9Om5ZHuLdUwovIzRy1ChhrZvbuKtStvgwEnEsS00B3Sw1WSs";
        Stripe::setApiKey($stripeSecretKey);
        $DOMAIN = 'http://localhost:8000/transaction';
        $checkout_session = \Stripe\Checkout\Session::create([
            'line_items' => [
                [
                    'price' => $this->getPrice("service",$totalPrice),
                    'quantity' => 1,    
                ]
            ],
            'mode' => 'payment',
            'success_url' => $DOMAIN . '/successpayement',
            'cancel_url' => $DOMAIN . '/fail',
        ]);
        return $this->redirect(
            $checkout_session->url
        );
    }

    public function getPrice($productName,$price):string{
        Stripe::setApiKey('sk_test_51MiCB1Gqpa2PZgAVrlv2LKXgwScv6giwP2nPMkB6pTxddtrFvf9Om5ZHuLdUwovIzRy1ChhrZvbuKtStvgwEnEsS00B3Sw1WSs');
        $product = Product::create([
            'name' => $productName,
        ]);
        $price = Price::create([
            'unit_amount' => $price,
            'currency' => 'usd',
            'product' => $product,
        ]);
        return $price->id;
    }


    #[Route('/thank-you', name: 'thank_you', methods: ['GET'])]
    public function thankYou(): Response
    {
        return $this->render('thank_you.html.twig');
    }
     
    #[Route('/front/test_trans', name: 'test_trans', methods: ['GET'])]
    public function test_trans(TransactionRepository $transactionRepository): Response
    {     // get your stripe public key
  
        return $this->render('templates_front/transaction/test.html.twig', [
            'transactions' => $transactionRepository->findAll(),
            'stripe_public_key' => "pk_test_51MiCB1Gqpa2PZgAVg7nHBkGdrBlceDIy56BnuktAq1sQSVy0Q5eugH3FmJxTFfGGMKYJuMqmy2JSrpCW8foyysl200WEy4rgut"
        ]);
        
    }


    
    #[Route('/', name: 'payAction', methods: ['POST'])]
    public function payAction(Request $request): Response
    {
        // Get the Stripe API key from the .env file
        $stripe = new StripeClient($_ENV['STRIPE_SECRET_KEY']);
    
        // Get the payment token and amount from the request
        $token = $request->request->get('stripeToken');
        $amount = $request->request->get('amount');
    
        try {
            // Charge the payment using the Stripe API
            $charge = $stripe->charges->create([
                'amount' => $amount,
                'currency' => 'usd',
                'source' => $token,
                'description' => 'Payment description',
            ]);
    
            // Payment succeeded, show success message
            return new Response('Payment successful');
        } catch (CardException $e) {
            // Payment failed, show error message
            return new Response('Payment failed: ' . $e->getMessage());
        }
    }
    
    #[Route('/stat', name: 'app_trans_stat', methods: ['GET'])]
    public function statindex(Request $request,EntityManagerInterface $entityManager,FlashyNotifier $flashy ): Response
    {
        $flashy->info('Statistic !!!!');

        $sumQueryBuilder = $entityManager
        ->createQueryBuilder()
        ->select('p.devis as material, SUM(p.montant_tot) as montan_totale')
        ->from(Transaction::class, 'p') 
        ->groupBy('p.devis');

        $weightSums = $sumQueryBuilder->getQuery()->getResult();

        // Transform the results into a format that Chart.js can use
        $weightData = [
            'labels' => [],
            'datasets' => [
                [
                    'label' => 'devis',
                    'data' => [],
                    'backgroundColor' => 'rgba(54, 162, 235, 0.2)',
                    'borderColor' => 'rgba(54, 162, 235, 1)',
                    'borderWidth' => 1
                ]
            ]
        ];

        foreach ($weightSums as $weightSum) {
            $weightData['labels'][] = $weightSum['material'];
            $weightData['datasets'][0]['data'][] = $weightSum['montan_totale'];
        }

        return $this->render('transaction/stat.html.twig', [
            'weightData' => $weightData,
            'weightSums' => $weightSums,
        ]);

    }
    #[Route('/', name: 'app_transaction_index', methods: ['GET'])]
    public function index(Request $request,TransactionRepository $transactionRepository,PaginatorInterface $paginator,EntityManagerInterface $entityManager): Response
    { 
        $searchTerm = $request->query->get('q');
    
        // Validate criteria parameter
        $validCriteria = ['devis', 'mode_trans', 'valeur_trans'];
        $criteria = $request->query->get('criteria');
        if ($criteria && !in_array($criteria, $validCriteria)) {
            return new Response('Invalid criteria parameter', 400);
        }
    
        // Build query
        $queryBuilder = $entityManager
            ->getRepository(Transaction::class)
            ->createQueryBuilder('c');
    
        if ($criteria === 'devis') {
            $queryBuilder->andWhere('c.devis LIKE :searchTerm')
                ->setParameter('searchTerm', '%' . $searchTerm . '%');
        } elseif ($criteria === 'mode_trans') {
            $queryBuilder->andWhere('c.mode_trans LIKE :searchTerm')
                ->setParameter('searchTerm', '%' . $searchTerm . '%');
        } elseif ($criteria === 'valeur_trans') {
            $queryBuilder->andWhere('c.valeur_trans LIKE :searchTerm')
                ->setParameter('searchTerm', '%' . $searchTerm . '%');
        }
    
        // Paginate results
        $pagination = $paginator->paginate(
            $queryBuilder->getQuery(),
            $request->query->getInt('page', 1),
            4
        );
    
        // Calculate weight sums
        $sumQueryBuilder = $entityManager
            ->createQueryBuilder()
            ->select('p.devis as material, SUM(p.valeur_trans) as montan_totale')
            ->from(Transaction::class, 'p')  
            ->groupBy('p.valeur_trans');
    
        $weightSums = $sumQueryBuilder->getQuery()->getResult();
    
        foreach ($weightSums as $weightSum) {
            $weightData['labels'][] = $weightSum['material'];
            $weightData['datasets'][0]['data'][] = $weightSum['montan_totale'];
        }
    
        return $this->render('transaction/index.html.twig', [
            'transactions' => $pagination,
            'weightSums' => $weightSums,
            'searchTerm' => $searchTerm,
        ]);
    }
    
    
    #[Route('/front/newtrans', name: 'app_transaction_newtrans', methods: ['GET'])]
    public function newtrans(TransactionRepository $transactionRepository,FlashyNotifier $flashy): Response
    { $flashy->info('welcome to payement !!!!');

      
        return $this->render('templates_front/transaction/index.html.twig', [
            'transactions' => $transactionRepository->findAll(),
            'stripe_public_key' => "pk_test_51MiCB1Gqpa2PZgAVg7nHBkGdrBlceDIy56BnuktAq1sQSVy0Q5eugH3FmJxTFfGGMKYJuMqmy2JSrpCW8foyysl200WEy4rgut"

        ]);
        
    }
    #[Route('/new', name: 'app_transaction_new', methods: ['GET', 'POST'])]
    public function new(Request $request, TransactionRepository $transactionRepository): Response
    {
        $transaction = new Transaction();
        $form = $this->createForm(TransactionType::class, $transaction);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Process the form data and save the transaction
            $transactionRepository->save($transaction, true);
            return $this->redirectToRoute('app_transaction_index', [], Response::HTTP_SEE_OTHER);
        }
    
        // If the form is not valid, add an error message to the form
        if ($form->isSubmitted() && !$form->isValid()) {
            $this->addFlash('error', 'Invalid data submitted!');
        }
    
        return $this->renderForm('transaction/new.html.twig', [
            'transaction' => $transaction,
            'form' => $form,
        ]);
    }
    
    #[Route('/{id_trans}', name: 'app_transaction_show', methods: ['GET'])]
    public function show(Transaction $transaction): Response
    {
        return $this->render('transaction/show.html.twig', [
            'transaction' => $transaction,
        ]);
    }

    #[Route('/{id_trans}/edit', name: 'app_transaction_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Transaction $transaction, TransactionRepository $transactionRepository): Response
    {
        $form = $this->createForm(TransactionType::class, $transaction);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $transactionRepository->save($transaction, true);

            return $this->redirectToRoute('app_transaction_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('transaction/edit.html.twig', [
            'transaction' => $transaction,
            'form' => $form,
        ]);
    }

    #[Route('/{id_trans}', name: 'app_transaction_delete', methods: ['POST'])]
    public function delete(Request $request, Transaction $transaction, TransactionRepository $transactionRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$transaction->getIdTrans(), $request->request->get('_token'))) {
            $transactionRepository->remove($transaction, true);
        }

        return $this->redirectToRoute('app_transaction_index', [], Response::HTTP_SEE_OTHER);
    }
}
