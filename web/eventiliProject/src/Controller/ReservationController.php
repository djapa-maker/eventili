<?php

namespace App\Controller;
//---------------------------------------------------------------------------------------
use App\Entity\Service;
use App\Entity\Reservation;
use App\Form\ReservationType;
use App\Repository\reservationRepository;
use App\Repository\ServiceRepository;
use App\Repository\SousserviceRepository;
use App\Repository\ImagessRepository;
use App\Repository\CategEventRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
//---------------------------------------------------------------------------------------
#[Route('/reservation')]
class ReservationController extends AbstractController
{
//---------------------------------------------------------------------------------------    
    #[Route('/', name: 'app_reservation_index', methods: ['GET'])]
    public function index(ImagessRepository $ImagessRepository,CategEventRepository $CategEventRepository,request $request,SousserviceRepository $SousserviceRepository,reservationRepository $reservationRepository,ServiceRepository $ServiceRepository): Response
    {
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
        // if ($request->isXmlHttpRequest()) {
        //     // If this is an AJAX request, return only the content for the 'my-div' element
        //     $souservices = $SousserviceRepository->findAll();
        //     $html = $this->renderView('templates_front/reservation/souservice_list.html.twig', ['souservices' => $souservices]);
        //     return new JsonResponse(['html' => $html]);
        // }
        $listimg = [];
        foreach ($sousservice as $serv) {
            $firstimg = $ImagessRepository->findBySousService($serv);
            if (!empty($firstimg)) {
                $fimg = $firstimg[0];
                $listimg[] = $fimg;
            }
        }
        return $this->render('templates_front/reservation/index.html.twig', [
            'reservations' => $reservationRepository->findAll(),
            'services'=>$ServiceRepository->findAll(),
            'souservices'=>$sousservice,
            'imagess'=>$ImagessRepository->findAll(),
            'firstimg' =>  $listimg
        ]);
    }
//---------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_reservation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, reservationRepository $reservationRepository): Response
    {
        $reservation = new Reservation();
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reservationRepository->save($reservation, true);

            return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('templates_front/reservation/new.html.twig', [
            'reservation' => $reservation,
            'form' => $form,
        ]);
    }
//---------------------------------------------------------------------------------------
    #[Route('/{idRes}', name: 'app_reservation_show', methods: ['GET'])]
    public function show(ImagessRepository $ImagessRepository,Reservation $reservation): Response
    {
        return $this->render('reservation/show.html.twig', [
            'reservation' => $reservation,
            
        ]);
    }
//---------------------------------------------------------------------------------------
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
//---------------------------------------------------------------------------------------
    #[Route('/{idRes}', name: 'app_reservation_delete', methods: ['POST'])]
    public function delete(Request $request, Reservation $reservation, reservationRepository $reservationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reservation->getIdRes(), $request->request->get('_token'))) {
            $reservationRepository->remove($reservation, true);
        }

        return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
    }

//-----------------------------fonction front ---------------------------------------------
    #[Route('/i/{idService}', name: 'app_reservation_indexres', methods: ['GET'])]
    public function indexres(CategEventRepository $CategEventRepository, ImagessRepository $ImagessRepository, $idService, Request $request, SousserviceRepository $SousserviceRepository, ReservationRepository $reservationRepository, ServiceRepository $ServiceRepository): Response
    {
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
        if ($request->isXmlHttpRequest()) {
            // If this is an AJAX request, return only the content for the 'my-div' element
            $souservices = $SousserviceRepository->findByIdService($idService);
            $html = $this->renderView('templates_front/reservation/souservice_list.html.twig', ['souservices' => $souservices]);
            return new JsonResponse(['html' => $html]);
        }
        $listimg = [];
        foreach ($sousservice as $serv) {
            $firstimg = $ImagessRepository->findBySousService($serv);
            if (!empty($firstimg)) {
                $fimg = $firstimg[0];
                $listimg[] = $fimg;
            }
        }
        // If this is not an AJAX request, render the full page as usual
        return $this->render('templates_front/reservation/index.html.twig', [
            'reservations' => $reservationRepository->findAll(),
            'eventCat' => $CategEventRepository->findAll(),
            'services' => $ServiceRepository->findAll(),
            'imagess' => $ImagessRepository->findAll(),
            'souservices' => $sousservice,
            'firstimg' =>  $listimg
        ]);
    }
//---------------------------------------------------------------------------------------
    #[Route('/{id}', name: 'app_reservation_detailsousservice', methods: ['GET'])]
    public function detail(SousserviceRepository $SousserviceRepository,ImagessRepository $ImagessRepository,Reservation $reservation): Response
    {

        return $this->render('reservation/show.html.twig', [
            'reservation' => $reservation,
            
        ]);
    }
//---------------------------------------------------------------------------------------
}
