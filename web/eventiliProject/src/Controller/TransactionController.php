<?php

namespace App\Controller;
use App\Entity\Reservation;
 use App\Entity\Sponsoring;
 use App\Entity\Transaction;
use App\Entity\Personne;

use App\Form\TransactionType;
use App\Repository\EvenementRepository;
 use App\Repository\ImagePersRepository;
 use App\Repository\PersonneRepository;
 use App\Repository\SponsoringRepository;
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
 use Symfony\Component\HttpFoundation\Session\SessionInterface;
 use Symfony\Component\Routing\Annotation\Route;
 use MercurySeries\FlashyBundle\FlashyNotifier;
 use Doctrine\ORM\Query;

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
    
        return $this->redirectToRoute('app_evenement_index');
    }
    #[Route('/payment', name: 'payment', methods: ['GET', 'POST'])]
    public function handlePayment(Request $request,EntityManagerInterface $entityManager
    , SessionInterface $session
    ): Response
    {
        $services = $session->get('services', []);
        $eventId = $session->get('eventId', []);
        $personne = $session->get('id');
         $serviceIds = [];
        foreach ($services as $service) {
            $serviceIds[] = $service->getId();
        }
        $serviceIdsString = implode(',', $serviceIds);

        $totalPrice =  $session->get('totalPrice')*100*3.15; //change------
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
            'success_url' => $DOMAIN . '/successpayement?id_pers='.$personne.'&serviceIdsString='.$serviceIdsString.'&eventId='.$eventId.'&totalPrice='.$totalPrice,
            'cancel_url' => $DOMAIN . '/fail',
        ]);
        return $this->redirect(
            $checkout_session->url
        );
 
    }
    #[Route('/successpayement', name: 'suc_payment', methods: ['GET'])]
    public function success(Request $request, EntityManagerInterface $entityManager, FlashyNotifier $flashy, SessionInterface $session, ImagePersRepository $imagePersRepository, 
    EvenementRepository $evenementRepository, PersonneRepository $personneRepository): Response
    {
        $sid = "AC9537e1a8c9bbac2628bc4e95783e4d69";
        $token = "3c4f2b98c7e5814b6d8c018abc7d322c";
        $twilio = new Client($sid, $token);
    
        $message = $twilio->messages
            ->create("+21628899807", // to
                array(
                    "from" => "+16206477906",
                    "body" => "payement successful"
                )
            );
    
        print($message->sid);
        $message = $twilio->messages
            ->create("whatsapp:+21628899807", // to
                array(
                    "from" => "whatsapp:+14155238886",
                    "body" => "payement succesful"
                )
            );
    
        print($message->sid);
    
        // Get the query parameters
        $personneId = $request->query->get('id_pers');
        $serviceIdsString = $request->query->get('serviceIdsString');//  nejmo n
        $eventId = $request->query->get('eventId');
    
        // Find the user entity by ID
        $user = $entityManager->find(Personne::class, $personneId);
      //7achtek bel block eli nseeti fih session ween nel9ah
 
      $session->set('id',$personneRepository->findOneByidPers( $personneId) );
            $session->set('personne', $personneId);
        // Create a new transaction
        $transaction = new Transaction();
        $transaction->setValeurTrans($request->query->get('totalPrice')/100);//-----change
        $transaction->setDevis('TND');
        $transaction->setDateTrans(new \DateTime('now', new DateTimeZone('America/New_York')));
        $transaction->setModeTrans('Stripe');
        $transaction->setMontantTot(($request->query->get('totalPrice')/100)+30);//-----change
    
        // Set the user ID for the transaction
        $transaction->setUserId($user);
    
        // Save the transaction to the database
        $entityManager->persist($transaction);
        $entityManager->flush();
    
        //--
        $reservation = new Reservation();
       $reservation->setIdSs($serviceIdsString);
        $reservation->setIdEv($evenementRepository->findOneByIdEv($eventId));
        $reservation->setStatus(false);
    
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($reservation);
        $entityManager->flush();
    
        //--
        // Generate the PDF file and return it as the response
        $pdf = $this->exportTransactionPdf($transaction, $request, $flashy);
        return $pdf;
    }
    
    

    
    #[Route('/fail', name: 'fail_payment')]
    public function fail(Request $request, EntityManagerInterface $entityManager): Response
    {
        $sid    = "AC9537e1a8c9bbac2628bc4e95783e4d69";
        $token  = "3c4f2b98c7e5814b6d8c018abc7d322c";
        $twilio = new Client($sid, $token);
    
        $message = $twilio->messages
          ->create("+21628899807", // to
            array(
              "from" => "+16206477906",
              "body" => "Your Message"
            )
          );
    
    print($message->sid);
        header('Location: http://localhost/fail.html');
        exit;
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
    
#[Route('/templates_back/stat', name: 'app_trans_stat', methods: ['GET'])]
public function statindex(Request $request, EntityManagerInterface $entityManager,
SessionInterface $sessions, FlashyNotifier $flashy): Response
{
    $flashy->info('Statistics');

    $sumQueryBuilder = $entityManager
        ->createQueryBuilder()
        ->select('p.devis as material, SUM(p.montant_tot) as montan_totale')
        ->from(Transaction::class, 'p') 
        ->groupBy('p.devis');

    $weightSummaries = $sumQueryBuilder->getQuery()->getResult(Query::HYDRATE_ARRAY);

    $weightData = [
        'labels' => array_column($weightSummaries, 'material'),
        'datasets' => [
            [
                'label' => 'devis',
                'data' => array_column($weightSummaries, 'montan_totale'),
                'backgroundColor' => 'rgba(54, 162, 235, 0.2)',
                'borderColor' => 'rgba(54, 162, 235, 1)',
                'borderWidth' => 1
            ]
        ]
    ];
         //----
         if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $sessions->set('last', $last);
        $last = $sessions->get('last');
        $personne = $sessions->get('id');
        //*** */
    return $this->render('/templates_back/transaction/stat.html.twig', [
        'weightData' => $weightData,
        'weightSummaries' => $weightSummaries,
                   
        'last' => $last,
        'personne' => $personne,
    ]);
}

    #[Route('/', name: 'app_transaction_index', methods: ['GET'])]
    public function index(Request $request,TransactionRepository $transactionRepository,
    PaginatorInterface $paginator,EntityManagerInterface $entityManager,
    SessionInterface $sessions
    ): Response
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
    //----
    if (!empty($images)) {
        $i = $images[0];
        $last = $i->getLast();
    } else {
        $last = "account (1).png";
    }
    $sessions->set('last', $last);
    $last = $sessions->get('last');
    $personne = $sessions->get('id');
    //*** */
        return $this->render('/templates_back/transaction/index.html.twig', [
            'transactions' => $pagination,
            'weightSums' => $weightSums,
            'searchTerm' => $searchTerm,
            'last' => $last,
            'personne' => $personne,
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
    public function new(Request $request, TransactionRepository $transactionRepository
    ,  SessionInterface $sessions): Response
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
     //----
     if (!empty($images)) {
        $i = $images[0];
        $last = $i->getLast();
    } else {
        $last = "account (1).png";
    }
    $sessions->set('last', $last);
    $last = $sessions->get('last');
    $personne = $sessions->get('id');
    //*** */
        return $this->renderForm('/templates_back/transaction/new.html.twig', [
            'transaction' => $transaction,
            'form' => $form,
            'last' => $last,
            'personne' => $personne,
        ]);
    }
    
    #[Route('/{id_trans}', name: 'app_transaction_show', methods: ['GET'])]
    public function show(Transaction $transaction
    ,SessionInterface $sessions): Response
    {
                 //----
                 if (!empty($images)) {
                    $i = $images[0];
                    $last = $i->getLast();
                } else {
                    $last = "account (1).png";
                }
                $sessions->set('last', $last);
                $last = $sessions->get('last');
                $personne = $sessions->get('id');
                //*** */
        return $this->render('/templates_back/transaction/show.html.twig', [
            'transaction' => $transaction,
            'last' => $last,
            'personne' => $personne,
        ]);
    }

    #[Route('/{id_trans}/edit', name: 'app_transaction_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Transaction $transaction,
    
    SessionInterface $sessions, TransactionRepository $transactionRepository): Response
    {
        $form = $this->createForm(TransactionType::class, $transaction);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $transactionRepository->save($transaction, true);

            return $this->redirectToRoute('app_transaction_index', [], Response::HTTP_SEE_OTHER);
        }
         //----
         if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $sessions->set('last', $last);
        $last = $sessions->get('last');
        $personne = $sessions->get('id');
        //*** */
        return $this->renderForm('/templates_back/transaction/edit.html.twig', [
            'transaction' => $transaction,
            'form' => $form,
            'last' => $last,
            'personne' => $personne,
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

    //sponsoring


#[Route('/transaction_sponsor', name: 'transaction_spn', methods: ['GET', 'POST'])]
public function addSponsoring(Request $request, EntityManagerInterface $entityManager): Response
{
    $session = $request->getSession();
    $amountSelection = $session->get('amountSelection');
    $impression = $session->get('impression');
    $dateDebut = $session->get('dateDebut');
    $dateFin = $session->get('dateFin');

    // Remove form data from session
    $session->remove('amountSelection');
    $session->remove('impression');
    $session->remove('dateDebut');
    $session->remove('dateFin');

    // Create new sponsoring object
    $sponsoring = new Sponsoring();
     $sponsoring->setNombreImpression($impression);
    $sponsoring->setDateDebut(new \DateTime($dateDebut));
    $sponsoring->setDateFin(new \DateTime($dateFin));

    // Persist object to database
    $entityManager->persist($sponsoring);
    $entityManager->flush();

    return $this->render('templates_front/sponsoring/display.html.twig', [
        'formData' => [
            'amountSelection' => $amountSelection,
            'impression' => $impression,
            'dateDebut' => $dateDebut,
            'dateFin' => $dateFin
        ]
    ]);
}

}
