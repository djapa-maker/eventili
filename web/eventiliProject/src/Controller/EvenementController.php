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
    public function new(Request $request, EvenementRepository $evenementRepository, PersonneRepository $PersonneRepository): Response
    {
        $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);
        $date = $request->get('event_date');
        $timeString = $request->get('event_time');
        $errorMessage = null;
        $errorMessage2 = null;
        

        if ($form->isSubmitted()) {
        list($startTime, $endTime) = array_map('trim', explode('-', $timeString));
        $startTimeStamp = strtotime($startTime);
        $endTimeStamp = strtotime($endTime);
            // var_dump($startTimeStamp);
            // var_dump($endTimeStamp);

            // die();

            if ($date === "") {
                $errorMessage = "La date est obligatoire.";
                $formIsValid = false;
            }
            else{
                $formIsValid = $form->isValid();
            }

            if($startTimeStamp > $endTimeStamp) {
                $errorMessage2 = "Heure debut doit etre inférieure à l heure fin.";
                $formIsValid = false;
            }
            else{
                $formIsValid = $form->isValid();
            }

            if ($formIsValid) {
                // var_dump($request->get('event_time'));
                // die();

                
                
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
                    // $evenement->setLimiteparticipant(0);
                }

                $evenement->setIdPers($PersonneRepository->findOneByIdPers(19));
                $evenementRepository->save($evenement, true);

                return $this->redirectToRoute('app_reservation_index');
            }
        }
        return $this->renderForm('templates_front/evenement/new.html.twig', [
            'evenements' => $evenement,
            'form' => $form,
            'errorMessage' => $errorMessage,
            'errorMessage2' => $errorMessage2,
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
        if ($this->isCsrfTokenValid('delete' . $evenement->getIdEv(), $request->request->get('_token'))) {
            $evenementRepository->remove($evenement, true);
        }

        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }
}
