<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Form\ReclamationType;
use App\Repository\ImagePersRepository;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/reclamation')]
class ReclamationController extends AbstractController
{
    #[Route('/', name: 'app_reclamation_index', methods: ['GET'])]
    public function index(ReclamationRepository $reclamationRepository,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
    {
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
        $ReclamOuverte = $reclamationRepository->findBy(['status'=>'ouvert']);
        $nbReclamOuverte = count($ReclamOuverte);
        if( $session->get('role') == "admin") {
            $ReclamOuverte = $reclamationRepository->findBy(['status' => 'EnAttenteRepUser']);
        } else {
            $ReclamOuverte = $reclamationRepository->findBy(['status' => 'EnAttenteRepAdmin']);
        }
        $nbReclamOuverte += count($ReclamOuverte);
        $ReclamCloturer = $reclamationRepository->findBy(['status' => 'cloture']);
        $nbReclamCloturer = count($ReclamCloturer);
        $ReclamEnAttente = $reclamationRepository->findBy(['status' => 'EnAttenteRepUser']);
        $nbReclamEnAtte = count($ReclamEnAttente);
        return $this->render('templates_back/reclamation/index.html.twig', [
            'personne' => $personne,
            'last'=> $last,
            'reclamations' => $reclamationRepository->findAll(),
            'nbReclamOuverte' => $nbReclamOuverte,
            'nbReclamCloturer' => $nbReclamCloturer,
            'nbReclamEnAttente' => $nbReclamEnAtte
        ]);

    }

    #[Route('/new', name: 'app_reclamation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ReclamationRepository $reclamationRepository): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($reclamation, true);

            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('templates_back/reclamation/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
        ]);
    }

    #[Route('/consulter/{idRec}', name: 'app_reclamation_show', methods: ['GET'])]
    public function consulter(Reclamation $reclamation,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
    {

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
        return $this->render('templates_back/reclamation/consulter.html.twig', [
            'personne' => $personne,
            'last'=> $last,
            'reclamation' => $reclamation,
        ]);
    }

    #[Route('/edit/{idRec}', name: 'app_reclamation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reclamation $reclamation, ReclamationRepository $reclamationRepository): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($reclamation, true);

            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('templates_back/reclamation/edit.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
        ]);
    }

    #[Route('/supprimer/{idRec}', name: 'app_reclamation_delete', methods: ['POST'])]
    public function delete(Request $request, Reclamation $reclamation, ReclamationRepository $reclamationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reclamation->getIdRec(), $request->request->get('_token'))) {
            $reclamationRepository->remove($reclamation, true);
        }

        return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
    }
}
