<?php

namespace App\Controller;

use App\Repository\EvenementRepository;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;
use Stripe\Product;
use App\Entity\Sponsoring;
use App\Form\SponsoringType;
use App\Repository\SponsoringRepository;
use Doctrine\ORM\EntityManagerInterface;
use Proxies\__CG__\App\Entity\Transaction;
use Stripe\Price;

use Stripe\Stripe;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\Flash\FlashBagInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Personne; 
use DateTimeZone; 

#[Route('/sponsoring')]
class SponsoringController extends AbstractController
{
    #[Route('/{id_sponso}/edit', name: 'app_sponsoring_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Sponsoring $sponsoring,
    SessionInterface $sessions, SponsoringRepository $sponsoringRepository): Response
    { //----
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
        $form = $this->createForm(SponsoringType::class, $sponsoring);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $sponsoringRepository->save($sponsoring, true);

            return $this->redirectToRoute('app_sponsoring_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('/templates_back/sponsoring/edit.html.twig', [
            'sponsoring' => $sponsoring,
            'form' => $form,
            'last' => $last,
            'personne' => $personne,
        ]);
    }
    #[Route('/', name: 'app_sponsoring_index', methods: ['GET'])]
    public function index(SponsoringRepository $sponsoringRepository,
    SessionInterface $sessions
    ): Response
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
        return $this->render('/templates_back/sponsoring/index.html.twig', [
            'sponsorings' => $sponsoringRepository->findAll(),
            
            'last' => $last,
            'personne' => $personne,
        ]);
    }

    #[Route('/paymentsponsosss', name: 'ssssssss', methods: ['GET', 'POST'])]
    public function handlePayment(
        Request $request, EntityManagerInterface $entityManager, SessionInterface $session
        , FlashBagInterface $flashBag

    ): Response {

        $amountSelection = $session->get('amount-label');
        $impression = $request->request->get('impression');
        $dateDebut = $request->request->get('date_debut');
        $dateFin = $request->request->get('date_fin');
        $totalPrice = $amountSelection;
        $idev = 350;//$session->get('idev');
$personne = $session->get('id');
 


$totalPrice = intval($impression)/15*100*0.3 ;

        $stripeSecretKey = "sk_test_51MiCB1Gqpa2PZgAVrlv2LKXgwScv6giwP2nPMkB6pTxddtrFvf9Om5ZHuLdUwovIzRy1ChhrZvbuKtStvgwEnEsS00B3Sw1WSs";
        Stripe::setApiKey($stripeSecretKey);
        $DOMAIN = 'http://localhost:8000/sponsoring';
        $checkout_session = \Stripe\Checkout\Session::create([
            'line_items' => [
                [
                    'price' => $this->getPrice("sponsore", $totalPrice),
                    'quantity' => 1,
                ]
            ],
            'mode' => 'payment',
            'success_url' => $DOMAIN . '/front/ajoutbase?amountSelection='.$totalPrice.'&impression='.$impression.'&dateDebut='.$dateDebut.'&dateFin='.$dateFin.'&id_pers='.$personne.'&idev='.$idev,
            'cancel_url' => $DOMAIN . '/fail',
        ]);
        return $this->redirect(
            $checkout_session->url
        );
    }
    #[Route('/front/ajoutbase', name: 'app_sponsoring_ajout_base', methods: ['GET', 'POST'])]
    public function ajoutbase(
        SponsoringRepository $sponsoringRepository, 
        EntityManagerInterface $entityManager,
        Request $request,SessionInterface $session ,ImagePersRepository $imagePersRepository,
        PersonneRepository $PersonneRepository, EvenementRepository $evenementRepository

    ): Response {


        
     $amountSelection = $request->query->get('amountSelection')/100;
        $impression = $request->query->get('impression');
        $dateDebut = $request->query->get('dateDebut');
        $dateFin = $request->query->get('dateFin');

        $transaction = new Transaction();
        $transaction->setMontantTot($amountSelection+30);//change
        $transaction->setValeurTrans($amountSelection);//change
        $transaction->setDevis('T?D');//change
        $transaction->setDateTrans(new \DateTime('now', new DateTimeZone('America/New_York')));
        $transaction->setModeTrans('Stripe');//change
         // Set other properties of the transaction object as needed
   
       $personne=$session->get('id');
      
         $idPerss = $session->get('personne'); 
         $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
         $images = array_reverse($images);
 
         if(!empty($images)){
             $i= $images[0];
             $last=$i->getLast();
         }
         else{
             $last="account (1).png";
         }
         $session->set('last', $last);
         $last=$session->get('last');
    
          //change
          $evenements = $evenementRepository->findOneByIdEv($request->query->get('idev'));
      $transaction->setUserId($PersonneRepository->findOneByIdPers($idPerss));
    $sponsoring = new Sponsoring();
        $sponsoring->setNombreImpression($impression);
        $sponsoring->setDateDebut(new \DateTime($dateDebut));
        $sponsoring->setDateFin(new \DateTime($dateFin));
        $sponsoring->setId_trans($transaction);
        $sponsoring->setIdEvent($evenements);//change


       $entityManager->persist($transaction);        
      $sponsoring->setId_trans($transaction);
       $entityManager->persist($sponsoring);
      $entityManager->flush();
      $personneId = $request->query->get('id_pers');
       
      $session->set('id',$PersonneRepository->findOneByidPers( $personneId) );
            $session->set('personne', $personneId);
            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }
    
     
    
    
    
    
    
    
    
    
    public function getPrice($productName, $price): string
    {
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





    #[Route('/front/ajout', name: 'app_sponsoring_ajout', methods: ['GET'])]
    public function indexajout(SponsoringRepository $sponsoringRepository
    ,SessionInterface $session ,ImagePersRepository $imagePersRepository): Response
    {         $personne=$session->get('id'); 
        $idPerss = $session->get('personne'); 
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);

        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }
       // dd($personne) ;
        $session->set('last', $last);
        $last=$session->get('last');
        return $this->render('templates_front/sponsoring/index.html.twig', [
            'sponsorings' => $sponsoringRepository->findAll(),
            'personne' => $personne,
            'last'=> $last,
        ]);
    }

    #[Route('/new', name: 'app_sponsoring_new', methods: ['GET', 'POST'])]
    public function new(Request $request, SponsoringRepository $sponsoringRepository,
    SessionInterface $sessions): Response
    { //----
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
        $sponsoring = new Sponsoring();
        $form = $this->createForm(SponsoringType::class, $sponsoring);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $sponsoringRepository->save($sponsoring, true);

            return $this->redirectToRoute('app_sponsoring_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('/templates_back/sponsoring/new.html.twig', [
            'sponsoring' => $sponsoring,
            'form' => $form,
            'last' => $last,
            'personne' => $personne,
        ]);
    }

    #[Route('/{id_sponso}', name: 'app_sponsoring_show', methods: ['GET'])]
    public function show(Sponsoring $sponsoring): Response
    {
        return $this->render('sponsoring/show.html.twig', [
            'sponsoring' => $sponsoring,
        ]);
    }



    #[Route('/{id_sponso}', name: 'app_sponsoring_delete', methods: ['POST'])]
    public function delete(Request $request, Sponsoring $sponsoring, SponsoringRepository $sponsoringRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $sponsoring->getIdSponso(), $request->request->get('_token'))) {
            $sponsoringRepository->remove($sponsoring, true);
        }

        return $this->redirectToRoute('app_sponsoring_index', [], Response::HTTP_SEE_OTHER);
    }
}