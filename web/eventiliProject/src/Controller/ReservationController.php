<?php

namespace App\Controller;

use App\Entity\Service;
use App\Entity\Reservation;
use App\Form\ReservationType;
use App\Repository\reservationRepository;
use App\Repository\ServiceRepository;
use App\Repository\ImagePersRepository;
use App\Repository\SousserviceRepository;
use App\Repository\ImagessRepository;
use App\Repository\CategEventRepository;
use App\Repository\AvisRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Repository\EvenementRepository;
use Dompdf\Dompdf;
use Dompdf\Options;


#[Route('/reservation')]
class ReservationController extends AbstractController
{
    //pdf-------------------------------------------------------------------
    // #[Route('/pdf', name: 'app_pdf')]
    // public function generatePdf()
    // {  
    //     $dompdf = new Dompdf();
         
    //     $dompdf->loadHtml('Brouette');
        
    //     $dompdf->render();

    //     $pdf= $dompdf->output();
    //     return new Response($pdf, 200, [
    //         'Content-Type' => 'application/pdf',
    //         'Content-Disposition' => 'inline; filename="example.pdf"',
    //     ]);
         
    // }
    #[Route('/pdf', name: 'app_pdf', methods: ['GET'])]
    public function generatePdf()
    {   
        
        // On définit les options du PDF
        $pdfOptions = new Options();
        // Police par défaut
        $pdfOptions->set('defaultFont', 'Arial');
        $pdfOptions->setIsRemoteEnabled(true);

        // On instancie Dompdf
        $dompdf = new Dompdf($pdfOptions);
        $context = stream_context_create([
            'ssl' => [
                'verify_peer' => FALSE,
                'verify_peer_name' => FALSE,
                'allow_self_signed' => TRUE
            ]
        ]);
        $dompdf->setHttpContext($context);
        // On génère le html
        $html = $this->renderView('templates_front/reservation/PDF.html.twig', [
        ]);

        $dompdf->loadHtml($html);
        $dompdf->setPaper('A4', 'portrait');
        $dompdf->render();

        // On génère un nom de fichier
        $fichier = 'formation-data-'.'.pdf';

        // On envoie le PDF au navigateur
        $dompdf->stream($fichier, [
            'Attachment' => true
            
        ]);

        return new Response();
    }
    //constructeur---------------------------------------------------------------------------------------    
    private $tabss;
    public function __construct(SousserviceRepository $sserviceRepository)
    {
        $sousservice = $sserviceRepository->findAll();
        $servicesArray = array_map(function ($sousservice) {
            return [
                'id' => $sousservice->getId(),
                'nom' => $sousservice->getNom(),
                'description' => $sousservice->getDescription(),
                'prix' => $sousservice->getPrix(),
                'note' => $sousservice->getNote(),
                'idEventcateg' => $sousservice->getIdEventcateg(),
                'idPers' => $sousservice->getIdPers(),
                'idService' => $sousservice->getIdService(),
                'imagess' => $sousservice->getImagess(),
                'ids' => $sousservice->getIdService()->getIdService(),
                'name' => $sousservice->getIdService()->getNom(),

            ];
        }, $sousservice);
        $this->tabss = $servicesArray;
        // dd($this->tabss);
    }
    //recherche-----------------------------------------------------------------------------------------------------------------
    #[Route('/search', name: 'app_reservation_search')]
    public function search(Request $request, SessionInterface $session, ImagePersRepository $imagePersRepository): JsonResponse
    {

        $searchTerm = $request->request->get('searchTerm');

        $list = $this->tabss;

        $results = array_filter($list, function ($item) use ($searchTerm) {
            return stripos($item['ids'], $searchTerm) !== false;
        });

        return $this->json(array_values($results));
    }

    // -------------------------------------------------------------------
    // #[Route('/', name: 'app_reservation_index', methods: ['GET'])]
    // public function index(AvisRepository $AvisRepository,ImagessRepository $ImagessRepository,CategEventRepository $CategEventRepository,request $request,SousserviceRepository $SousserviceRepository,reservationRepository $reservationRepository,ServiceRepository $ServiceRepository): Response
    // {
    //     $sousservice = $SousserviceRepository->findAll();
    //     foreach ($sousservice as $s) {
    //         $checkboxes = explode(',', $s->getIdEventcateg());
    //         $list = [];
    //         foreach ($checkboxes as $c) {
    //             $list[] = $CategEventRepository->findOneByIdCateg($c)->getType();
    //         }
    //         $selectedCheckboxes = implode(',', $list);
    //         $s->setIdEventcateg($selectedCheckboxes);
    //     }
    //     $listimg = [];
    //     foreach ($sousservice as $serv) {
    //         $firstimg = $ImagessRepository->findBySousService($serv);
    //         if (!empty($firstimg)) {
    //             $fimg = $firstimg[0];
    //             $listimg[] = $fimg;
    //         }
    //     }
    //     $av=$AvisRepository->findAll();
    //     return $this->render('templates_front/reservation/index.html.twig', [
    //         'reservations' => $reservationRepository->findAll(),
    //         'services'=>$ServiceRepository->findAll(),
    //         'souservices'=>$sousservice,
    //         'imagess'=>$ImagessRepository->findAll(),
    //         'firstimg' =>  $listimg,
    //         'avis'=>$av
    //     ]);
    // }
//index de la pill tout -----------------------------------------------------------------------------------------------------------------
    #[Route('/', name: 'app_reservation_index', methods: ['GET'])]
    public function index(ImagePersRepository $imagePersRepository, AvisRepository $AvisRepository, SessionInterface $session, ImagessRepository $ImagessRepository, CategEventRepository $CategEventRepository, request $request, SousserviceRepository $SousserviceRepository, reservationRepository $reservationRepository, ServiceRepository $ServiceRepository): Response
    {
        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);

        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');

        $sousservice = $SousserviceRepository->findAll();
        foreach ($sousservice as $s) {
            $checkboxes = explode(',', $s->getIdEventcateg());
            $list = [];
            foreach ($checkboxes as $c) {
                $list[] = $CategEventRepository->findOneByIdCateg($c)->getType();
            }
            $selectedCheckboxes = implode(',', $list);
            $s->setIdEventcateg($selectedCheckboxes);
        }
        $listimg = [];
        foreach ($sousservice as $serv) {
            $firstimg = $ImagessRepository->findBySousService($serv);
            if (!empty($firstimg)) {
                $fimg = $firstimg[0];
                $listimg[] = $fimg;
            }
        }
        $av = $AvisRepository->findAll();
        //---------------------------------Chaima-------------------------//
        foreach ($sousservice as $serv) {
            $imgfirst[$serv->getId()] = $ImagessRepository->findBy(['sousService' => $serv->getId()]);
        }
        // $evenement = $evenementRepository->findOneBy(['idEv' => $request->query->get('event')]);
        $idEv = $request->query->get('event');
        $eventId = $session->get('eventId', []);
        $session->set('eventId', $idEv);
        // dump($idEv);
        // die();
        // dump($session);

        // $services = $session->get('services', []);
        // dump($eventId);
        // die();

        return $this->render('templates_front/reservation/index.html.twig', [
            // 'reservations' => $reservationRepository->findAll(),
            'services' => $ServiceRepository->findAll(),
            'souservices' => $sousservice,
            'imagess' => $ImagessRepository->findAll(),
            'firstimg' => $listimg,
            'imgfirst' => $imgfirst,
            'avis' => $av,
            'personne' => $personne,
            'last' => $last
        ]);
    }
    //add service to session ---------------------------------------------------------------------------------------
    #[Route('/reservice/add', name: 'app_reservice_add', methods: ['POST'])]
    public function addService(SousserviceRepository $SousserviceRepository, Request $request, SessionInterface $session)
    {

        $data = json_decode($request->getContent(), true);
        $serviceId = $data['serviceId'];
        $service = $SousserviceRepository->find($serviceId);

        if (!$service) {
            return new JsonResponse(['success' => false, 'message' => 'Service non trouvé.'], 404);
        }

        $services = $session->get('services', []);
        $services[$serviceId] = $service;
        $session->set('services', $services);
        return new JsonResponse(['success' => true, 'message' => 'Service ajouté à la session.', 'service' => $service]);
    }
       //remove session ---------------------------------------------------------------------------------------
       #[Route('/reservice/remove', name: 'app_reservice_remove', methods: ['POST'])]
       public function removeService(Request $request, SessionInterface $session)
       {
           // Récupérer l'id du service a supprimer
           $data = json_decode($request->getContent(), true);
           $serviceId = $data['serviceId'];
   
           // Supprimer le service de la session
           $services = $session->get('services', []);
           // dump($services);
           // die();
           unset($services[$serviceId]);
           $session->set('services', $services);
   
           return new JsonResponse(['success' => true, 'message' => 'Service supprimé de la session.']);
       }
   
    //---------------------------------------------Alaa--------------------------
    #[Route('/new', name: 'app_reservation_new', methods: ['GET', 'POST'])]
    public function new(Request $request,EvenementRepository $evenementRepository, reservationRepository $reservationRepository, SessionInterface $session): Response
    {
        $reservation = new Reservation();
        $services = $session->get('services', []);
        $eventId = $session->get('eventId', []);
        $serviceIds = [];
        foreach ($services as $service) {
            $serviceIds[] = $service->getId();
        }
        $serviceIdsString = implode(',', $serviceIds);
        $reservation->setIdSs($serviceIdsString);
        $reservation->setIdEv($evenementRepository->findOneByIdEv($eventId));
        $reservation->setStatus(false);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($reservation);
        $entityManager->flush();


        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }
    //detail de reservation ---------------------------------------------------------------------------------------
    #[Route('/{idRes}', name: 'app_reservation_show', methods: ['GET'])]
    public function show(ImagessRepository $ImagessRepository, Reservation $reservation): Response
    {
        return $this->render('reservation/show.html.twig', [
            'reservation' => $reservation,

        ]);
    }
    //modification de la reservation ---------------------------------------------------------------------------------------
    #[Route('/{idRes}/edit', name: 'app_reservation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reservation $reservation, reservationRepository $reservationRepository): Response
    {
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reservationRepository->save($reservation, true);

            return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reservation/edit.html.twig', [
            'reservation' => $reservation,
            'form' => $form,
        ]);
    }
    //suppression d'une reservation---------------------------------------------------------------------------------------
    #[Route('/{idRes}', name: 'app_reservation_delete', methods: ['POST'])]
    public function delete(Request $request, Reservation $reservation, reservationRepository $reservationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $reservation->getIdRes(), $request->request->get('_token'))) {
            $reservationRepository->remove($reservation, true);
        }

        return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
    }

    //-----------------------------fonction front ---------------------------------------------
    // #[Route('/i/{idService}', name: 'app_reservation_indexres', methods: ['GET'])]
    // public function indexres($idService,AvisRepository $AvisRepository,CategEventRepository $CategEventRepository, ImagessRepository $ImagessRepository,  Request $request, SousserviceRepository $SousserviceRepository, ReservationRepository $reservationRepository, ServiceRepository $ServiceRepository): Response
    // {
    //     $sousservice = $SousserviceRepository->findByIdService($idService);
    //     foreach ($sousservice as $s) {
    //         $checkboxes = explode(',', $s->getIdEventcateg());
    //         $list = [];
    //         foreach ($checkboxes as $c) {
    //             $list[] = $CategEventRepository->findOneByIdCateg($c)->getType();
    //         }
    //         $selectedCheckboxes = implode(',', $list);
    //         $s->setIdEventcateg($selectedCheckboxes);
    //     }
    //     if ($request->isXmlHttpRequest()) {
    //         // If this is an AJAX request, return only the content for the 'my-div' element
    //         $souservices = $SousserviceRepository->findByIdService($idService);
    //         $html = $this->renderView('templates_front/reservation/souservice_list.html.twig', ['souservices' => $souservices]);
    //         return new JsonResponse(['html' => $html]);
    //     }
    //     $listimg = [];
    //     foreach ($sousservice as $serv) {
    //         $firstimg = $ImagessRepository->findBySousService($serv);
    //         if (!empty($firstimg)) {
    //             $fimg = $firstimg[0];
    //             $listimg[] = $fimg;
    //         }
    //     }
    //     $av=$AvisRepository->findAll();
    //     return $this->render('templates_front/reservation/index.html.twig', [
    //         'reservations' => $reservationRepository->findAll(),
    //         'eventCat' => $CategEventRepository->findAll(),
    //         'services' => $ServiceRepository->findAll(),
    //         'imagess' => $ImagessRepository->findAll(),
    //         'souservices' => $sousservice,
    //         'firstimg' =>  $listimg,
    //         'avis'=>$av
    //     ]);
    // }
//affichage des sous services par service les autres pill -----------------------------------------------------------------------------------------------------------------
    #[Route('/i/{idService}', name: 'app_reservation_indexres', methods: ['GET'])]
    public function indexres(SessionInterface $session, ImagePersRepository $imagePersRepository, AvisRepository $AvisRepository, CategEventRepository $CategEventRepository, ImagessRepository $ImagessRepository, $idService, Request $request, SousserviceRepository $SousserviceRepository, ReservationRepository $reservationRepository, ServiceRepository $ServiceRepository): Response
    {
        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);

        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');

        $sousservices2 = $SousserviceRepository->findAll();
        $sousservice = $SousserviceRepository->findByIdService($idService);
        foreach ($sousservice as $s) {
            $checkboxes = explode(',', $s->getIdEventcateg());
            $list = [];
            foreach ($checkboxes as $c) {
                $list[] = $CategEventRepository->findOneByIdCateg($c)->getType();
            }
            $selectedCheckboxes = implode(',', $list);
            $s->setIdEventcateg($selectedCheckboxes);
        }

        $listimg = [];
        foreach ($sousservice as $serv) {
            $firstimg = $ImagessRepository->findBySousService($serv);
            if (!empty($firstimg)) {
                $fimg = $firstimg[0];
                $listimg[] = $fimg;
            }
        }
        $av = $AvisRepository->findAll();
        //chaima
        foreach ($sousservices2 as $serv) {
            $imgfirst[$serv->getId()] = $ImagessRepository->findBy(['sousService' => $serv->getId()]);
        }
        return $this->render('templates_front/reservation/index.html.twig', [
            'reservations' => $reservationRepository->findAll(),
            'eventCat' => $CategEventRepository->findAll(),
            'services' => $ServiceRepository->findAll(),
            'imagess' => $ImagessRepository->findAll(),
            'souservices' => $sousservice,
            'firstimg' => $listimg,
            'imgfirst' => $imgfirst,
            'avis' => $av,
            'personne' => $personne,
            'last' => $last
        ]);
    }
    //detail reservation ---------------------------------------------------------------------------------------
    #[Route('/{id}', name: 'app_reservation_detailsousservice', methods: ['GET'])]
    public function detail(SousserviceRepository $SousserviceRepository, ImagessRepository $ImagessRepository, Reservation $reservation): Response
    {
        return $this->render('reservation/show.html.twig', [
            'reservation' => $reservation,

        ]);
    }
    //affichage des ss par ajax -----------------------------------------------------------------------------------------------------------------    
    #[Route('/loadsousservices', name: 'app_load_sous_services')]
    public function loadSousServices(SessionInterface $session, ImagePersRepository $imagePersRepository, Request $request): JsonResponse
    {
        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);

        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');
        $serviceId = $request->request->get('searchTerm');
        $list = $this->tabss;

        $results = array_filter($list, function ($item) use ($serviceId) {
            return stripos($item['name'], $serviceId) !== false;
        });
        // $html = $this->renderView('templates_front/reservation/index.html.twig', ['sousServices' => $sousServices]);

        return $this->json(array_values($results));
    }
//recherche dynamic -----------------------------------------------------------------------------
// #[Route('/searchCateg', name: 'app_reservation_searchCateg')]
// public function searchCateg(Request $request): JsonResponse
// {
//     $searchTerm = $request->request->get('searchTerm');

//     $list = $this->tabss;

//     $results = array_filter($list, function ($item) use ($searchTerm) {
//         return stripos($item['name'], $searchTerm) !== false;
//     });
// }
//     return $this->json(array_values($results));
// }
// #[Route('/ajax/souservices/{idService}', name: 'app_reservation_souservices', methods: ['GET'])]
// public function souservices($idService, SousserviceRepository $SousserviceRepository): Response
// {
//     $souservices = $SousserviceRepository->findByIdService($idService);
//     $html = $this->renderView('templates_front/reservation/souservice_list.html.twig', ['souservices' => $souservices]);
//     return new JsonResponse(['html' => $html]);
// }

}