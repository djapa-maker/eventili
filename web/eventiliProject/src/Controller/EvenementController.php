<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\Imgev;
use App\Form\EvenementType;
use App\Repository\CategEventRepository;
use App\Repository\EvenementRepository;
use App\Repository\ImgevRepository;
use App\Repository\PersonneRepository;
use App\Repository\SousserviceRepository;
use App\Repository\SponsoringRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\ImagePersRepository;
use App\Repository\reservationRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;
use App\Repository\ImagessRepository;
#[Route('/evenement')]
class EvenementController extends AbstractController
{

    #[Route('/event', name: 'app_event_affich', methods: ['GET'])]
    public function affich(ImagePersRepository $imagePersRepository, SessionInterface $session): Response
    {
        $idPerss = $session->get('personne'); 
        $personne = $session->get('id'); 
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }
        
        return $this->render('templates_front/personne/eventsprofil.html.twig', [
                'images' => $images,
                'last' => $last,
                'personne' => $personne,
            ]);
        

       
    }
//affichage-------------------------------------------------------------------------------------------------------------------------------------------
    #[Route('/', name: 'app_evenement_index', methods: ['GET'])]
    public function index(
        EntityManagerInterface $entityManager
        ,ImagessRepository $ImagessRepository
        ,SousserviceRepository $SousserviceRepository
        , reservationRepository $reservationRepository,
        SponsoringRepository $sponsoringRepository, ImagePersRepository $imagePersRepository, EvenementRepository $evenementRepository, SessionInterface $session, CategEventRepository $categEventRepository, ImgevRepository $imgevRepository): Response
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
        //-----------------------------------------------------
        $Categ = $categEventRepository->findAll();
        $evenements = $evenementRepository->findBy(['idPers' => $idPerss]);
        //dd($evenements);
        $imgev = [];
        $list= [];
        $listSS =[];
        $listimg = [];
        $listsponso = [];
        $session->remove('eventId');
        $session->remove('services');
        //--------
        foreach ($evenements as $event) {
  $sponsoevent[$event->getIdEv()] = $sponsoringRepository->findOneBy(['id_event' => $event->getIdEv()]);

        }  
        //--------
        foreach ($evenements as $event) {
            $reservation = $reservationRepository->findOneBy(['idEv' => $event->getIdEv()]);
            if(!$reservation){
                $evenementRepository->remove($event, true);
            } 
        }
        $events = $evenementRepository->findBy(['idPers' => $idPerss]);
        //dd($events);
        //----------------------------------------------------------
        foreach ($evenements as $event) {
            $imgev[$event->getIdEv()]= $imgevRepository->findBy(['idEven' => $event->getIdEV()]);
            $res = $reservationRepository->findOneByIdEv($event->getIdev());
            $list[$event->getIdEv()]= explode(',', $res->getIdSs());
            foreach ($list[$event->getIdEv()] as $ss) {
                $listSS[$event->getIdEv()][]=$SousserviceRepository->findOneById($ss);
            }
          
        }  
        //first image mta3 el sousservice 
       
        foreach ($list as $serv) {
            $firstimg = $ImagessRepository->findBySousService($serv);
            if (!empty($firstimg)) {
                $fimg = $firstimg[0]; 
            }
            
        }
        //-------------------------------
        $listimg[]= $fimg;
        return $this->render('templates_front/evenement/index.html.twig', [
            'evenements' => $events,
            'Categ' => $Categ,
            'Img' => $imgev,
            'personne' => $personne,
            'sous' => $listSS,
            'firstimg' =>  $listimg,
            'last' => $last,
            'eventsponso'=>  $sponsoevent,
        ]);
    }
//ajout-----------------------------------------------------------------------------------------------
#[Route('/new', name: 'app_evenement_new', methods: ['GET', 'POST'])]
public function new(SessionInterface $session, ImagePersRepository $imagePersRepository, Request $request, EvenementRepository $evenementRepository, PersonneRepository $PersonneRepository): Response
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
    //--------------------------------------------------------------------

    $evenement = new Evenement();
    $form = $this->createForm(EvenementType::class, $evenement);
    $form->handleRequest($request);
    $errorMessage = null;
    $errorMessage2 = null;
    $errorMessage3 = null;
    $errorMessage4 = null;
    $errorMessage5 = null;
    $allImagesValid = true;

    if ($form->isSubmitted()) {
        $date = $request->get('event_date');
        $timeString = $request->get('event_time');
        list($startTime, $endTime) = array_map('trim', explode('-', $timeString));
        $startTimeStamp = strtotime($startTime);
        $endTimeStamp = strtotime($endTime);
        $images = $form->get('imgev')->getData();

        if ($date === "" || $startTimeStamp > $endTimeStamp || empty($images) || $images[0]->getError() == UPLOAD_ERR_NO_FILE) {
            // If any of the conditions is true, set the appropriate error message and set $formIsValid to false
            if ($date === "") {
                $errorMessage = "La date est obligatoire.";
            }
            if ($startTimeStamp > $endTimeStamp) {
                $errorMessage3 = "Heure debut doit etre inférieure à l'heure fin.";
            }
            if (empty($images) || $images[0]->getError() == UPLOAD_ERR_NO_FILE) {
                $errorMessage4 = "Il faut choisir au moin une image";
            }
            $formIsValid = false;
        } else {
            // If all conditions are false, move the uploaded image files and set $formIsValid to true
            foreach ($images as $image) {
                if (!in_array($image->getMimeType(), ['image/jpeg', 'image/png', 'image/gif'])) {
                    $errorMessage5 = "Merci d'entrer des images valides";
                    $formIsValid = false;
                    $allImagesValid = false;
                } else {
                    $fichier = md5(uniqid()) . '.' . $image->guessExtension();
                    $image->move(
                        $this->getParameter('images_directory'),
                        $fichier
                    );
                    $img = new Imgev();
                    $img->setImagee($fichier);
                    $evenement->addImage($img);
                    if ($allImagesValid) {
                        $formIsValid = $form->isValid();
                    }
                }
            }
        }

        if ($formIsValid) {
            $dateDebString = $date . ' ' . $startTime;
            $dateFinString = $date . ' ' . $endTime;
            $dateDeb = new \DateTime($dateDebString);
            $dateFin = new \DateTime($dateFinString);

            $evenement->setDateDebut($dateDeb);
            $evenement->setDateFin($dateFin);
            $evenement->setVisibilite('Privé');

            $prix = $form->get('prix')->getData();
            $nbticket = $form->get('limiteparticipant')->getData();

            // var_dump($prix);
            // die();
            $evenement->setPrix($prix);
            $evenement->setLimiteparticipant($nbticket);
            if ($prix != "0") {
                $evenement->setType('Payant');
            } else {
                $evenement->setType('Gratuit');
                // $evenement->setPrix(0);
                $evenement->setLimiteparticipant(0);
            }

            $evenement->setIdPers($PersonneRepository->findOneByIdPers($personne));
            //$evenementRepository->save($evenement, true);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($evenement);
            $entityManager->flush();

            return $this->redirectToRoute('app_reservation_index', ['event' => $evenement->getIdEv()]);
        }
    }
    return $this->renderForm('templates_front/evenement/new.html.twig', [
        'evenements' => $evenement,
        'form' => $form,
        'errorMessage' => $errorMessage,
        'errorMessage2' => $errorMessage2,
        'errorMessage3' => $errorMessage3,
        'errorMessage4' => $errorMessage4,
        'errorMessage5' => $errorMessage5,
        'personne' => $personne,
        'last' => $last,
    ]);
}


//supprimer ------------------------------------------------------------------------------------------------
#[Route('/{idEv}', name: 'app_evenement_delete', methods: ['POST'])]
public function delete(FlashyNotifier $flashy, Request $request, Evenement $evenement, EvenementRepository $evenementRepository): Response
{
    if ($this->isCsrfTokenValid('delete' . $evenement->getIdEv(), $request->request->get('_token'))) {
        $evenementRepository->remove($evenement, true);
        $titre = $evenement->getTitre();
        $flashy->success("$titre a été supprimé !");
        
    }

    return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
}
//editVisibility----------------------------------------------------------------------------
#[Route('/editVis/{id}', name: 'app_evenement_editVis', methods: ['GET', 'POST'])]
public function editVisibility(Request $request, EvenementRepository $evenementRepository, $id): Response
{
    
    $event = $evenementRepository->findOneBy(['idEv' =>$id]);
    $event->setVisibilite("Public");
    $evenementRepository->save($event, true);

    return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);

}
//----------------------------------------------------------------------------
    #[Route('/{idEv}/edit', name: 'app_evenement_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Evenement $evenement, EvenementRepository $evenementRepository): Response
    {
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $evenementRepository->save($evenement, true);

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('evenement/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }
}
