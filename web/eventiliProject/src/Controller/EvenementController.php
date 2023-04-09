<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Form\EvenementType;
use App\Repository\CategEventRepository;
use App\Repository\EvenementRepository;
use App\Repository\PersonneRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/evenement')]
class EvenementController extends AbstractController
{
    #[Route('/', name: 'app_evenement_index', methods: ['GET'])]
    public function index(EvenementRepository $evenementRepository): Response
    {
        return $this->render('templates_front/evenement/index.html.twig', [
            'evenements' => $evenementRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_evenement_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EvenementRepository $evenementRepository, CategEventRepository $CategEventRepository, PersonneRepository $PersonneRepository): Response
    {
        $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $evenement->setVisibilite('Privé');
            $activeTab = $request->request->get('activeTab');
            if ($activeTab == 'gratuit-tab'){
                $evenement->setType('Gratuit');
                $evenement->setPrix(0);
                $evenement->setLimiteparticipant(0);
            }
            else{
                $evenement->setType('Payant');
            }
            $date = $request->get('event_date');
            $start_time = $request->get('event_time')['start'];
            $end_time = $request->get('event_time')['end'];
            $dateDebString = $date . ' ' . $start_time;
            $dateFinString = $date . ' ' . $end_time;
            $dateDeb = \DateTime::createFromFormat('Y-m-d H:i', $dateDebString);
            $dateFin = \DateTime::createFromFormat('Y-m-d H:i', $dateFinString);
            $evenement->setDateDebut($dateDeb);
            $evenement->setDateFin($dateFin);
            $evenement->setIdPers($PersonneRepository->findOneByIdPers(19));
            

            $evenementRepository->save($evenement, true);

            // return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('templates_front/evenement/new.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
            // 'eventCat' => $CategEventRepository->findAll(),
        ]);
    }

    #[Route('/{idEv}', name: 'app_evenement_show', methods: ['GET'])]
    public function show(Evenement $evenement): Response
    {
        return $this->render('evenement/show.html.twig', [
            'evenement' => $evenement,
        ]);
    }

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

    #[Route('/{idEv}', name: 'app_evenement_delete', methods: ['POST'])]
    public function delete(Request $request, Evenement $evenement, EvenementRepository $evenementRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$evenement->getIdEv(), $request->request->get('_token'))) {
            $evenementRepository->remove($evenement, true);
        }

        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }
}
