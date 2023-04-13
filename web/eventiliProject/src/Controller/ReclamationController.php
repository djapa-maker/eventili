<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Entity\Reponse;
use App\Form\ReclamationAjouterType;
use App\Form\ReclamationType;
use App\Form\ReponseType;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;
use App\Repository\ReclamationRepository;
use App\Repository\ReponseRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/reclamation')]
class ReclamationController extends AbstractController
{
    // Index génerale de la reclamation
    #[Route('/', name: 'app_reclamation_index', methods: ['GET'])]
    public function index(request $request,SessionInterface $session,PersonneRepository $personneRepository): Response
    {
        $personne=$session->get('id');
        var_dump($personne);
        if( $personne->getRole() == "admin") {
            return $this->redirectToRoute('app_reclamation_admin_index');
        }
        return $this->redirectToRoute('app_reclamation_user_index');
    }
    #[Route('/admin', name: 'app_reclamation_admin_index', methods: ['GET'])]
    public function admin_index(ReclamationRepository $reclamationRepository,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository,PersonneRepository $persRepo): Response{

        $personne=$session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        $role = $personne->getRole();

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
        $ReclamOuverte = $reclamationRepository->findBy(['status' => 'EnAttenteRepAdmin']);
        $nbReclamOuverte += count($ReclamOuverte);
        $ReclamCloturer = $reclamationRepository->findBy(['status' => 'cloturer']);
        $nbReclamCloturer = count($ReclamCloturer);
        $ReclamEnAttente = $reclamationRepository->findBy(['status' => 'EnAttenteRepUser']);
        $nbReclamEnAtte = count($ReclamEnAttente);
        $reclamationTab = [];
        $reclamationss = $reclamationRepository->findAll();


        return $this->render('templates_back/reclamation/index.html.twig', [
            'personne' => $personne,
            'last'=> $last,
            'reclamations' => $reclamationss,
            'nbReclamOuverte' => $nbReclamOuverte,
            'nbReclamCloturer' => $nbReclamCloturer,
            'nbReclamEnAttente' => $nbReclamEnAtte,
            'roles' => $role
        ]);
    }
    #[Route('/user', name: 'app_reclamation_user_index', methods: ['GET'])]
    public function user_index(request $request,SessionInterface $session,ImagePersRepository $imagePersRepository,ReclamationRepository $reclamationRepository):Response
    {
        $personne=$session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        $role = $personne->getRole();

        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }
        $session->set('last', $last);
        $last=$session->get('last');
        $reclamationss = $reclamationRepository->findAll();
        return $this->render('templates_front/reclamation/index.html.twig',[
            'personne' => $personne,
            'last'=> $last,
            'reclamations' => $reclamationss
        ]);
    }
    #[Route('/ajouter', name: 'app_reclamation_ajouter', methods: ['GET', 'POST'])]
    public function ajouter(request $request,SessionInterface $session,ImagePersRepository $imagePersRepository,ReclamationRepository $reclamationRepository):Response{
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
        $reclam = new Reclamation();
        $form = $this->createForm(ReclamationAjouterType::class,$reclam,[
            'sender' => $idPerss,
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($reclam, true);
            return $this->redirectToRoute('app_reclamation_user_consulter', ['idRec' => $reclam->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        return $this->render('templates_front/reclamation/ajouter.html.twig',[
            'personne' => $personne,
            'last'=> $last,
            'form' => $view
        ]);
    }
    #[Route('/user/consulter/{idRec}', name: 'app_reclamation_user_consulter', methods: ['GET', 'POST'])]
    public function consulterr(Reclamation $idRec,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository, ReponseRepository $repRepo, ReclamationRepository $recRepo): Response
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
        $date = $idRec->getDateheure()->format('d/m/y H:i');
        $reps = $repRepo->findBy(['rec' => $idRec]);
        $idUser = $session->get('id')->getIdPers();
        $Message = new Reponse();
        $form = $this->createForm(ReponseType::class,$Message,[
            'sender' => $idUser,
            'rec' => $idRec->getIdRec(),
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $repRepo->save($Message, true);
            $idRec->setStatus("EnAttenteRepAdmin");
            $recRepo->save($idRec,true);
            return $this->redirectToRoute('app_reclamation_admin_consulter', ['idRec' => $idRec->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        return $this->render('templates_front/reclamation/consulter.html.twig',[
            'personne' => $personne,
            'last'=> $last,
            'reclamation' => $idRec,
            'date' => $date,
            'reps' => $reps,
            'uid' => $idUser,
            'message' => $Message,
            'form' => $view
        ]);
    }
    #[Route('/admin/consulter/{idRec}', name: 'app_reclamation_admin_consulter', methods: ['GET','POST'])]
    public function consulter(Reclamation $idRec,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository, ReponseRepository $repRepo, ReclamationRepository $recRepo): Response
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
        $date = $idRec->getDateheure()->format('d/m/y H:i');
        $reps = $repRepo->findBy(['rec' => $idRec]);
        $idUser = $session->get('id')->getIdPers();
        $Message = new Reponse();
        $form = $this->createForm(ReponseType::class,$Message,[
            'sender' => $idUser,
            'rec' => $idRec->getIdRec(),
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $repRepo->save($Message, true);
            $idRec->setStatus("EnAttenteRepUser");
            $recRepo->save($idRec,true);
            return $this->redirectToRoute('app_reclamation_admin_consulter', ['idRec' => $idRec->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        return $this->render('templates_back/reclamation/consulter.html.twig', [
            'personne' => $personne,
            'last'=> $last,
            'reclamation' => $idRec,
            'date' => $date,
            'reps' => $reps,
            'uid' => $idUser,
            'message' => $Message,
            'form' => $view
        ]);
    }
    #[Route('/modifier/{idRec}', name: 'app_reclamation_modifier', methods: ['GET', 'POST'])]
    public function modifier(Reclamation $idRec,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository,ReclamationRepository $reclamationRepository){
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
        $form = $this->createForm(ReclamationType::class, $idRec,['desc' => $idRec->getDescription()]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($idRec, true);
            return $this->redirectToRoute('app_reclamation_admin_consulter', ['idRec' => $idRec->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        return $this->render('templates_back/reclamation/modifier.html.twig', [
            'personne' => $personne,
            'last'=> $last,
            'reclamation' => $idRec,
            'form' => $view
        ]);
    }
    #[Route('/cloturer/{idRec}', name: 'app_reclamation_cloturer', methods: ['POST'])]
    public function cloturer(Request $request, Reclamation $reclamation,SessionInterface $session, ReclamationRepository $reclamationRepository): Response{
        $session->getFlashBag()->clear();
        if ($this->isCsrfTokenValid('cloturer'.$reclamation->getIdRec(), $request->request->get('_token'))) {
            try{
                $reclamation->setStatus("cloturer");
                $reclamationRepository->save($reclamation,true);
                $this->addFlash('success', 'Réclamation a été cloturé avec success');
            } catch (\Exception $e){
                $this->addFlash('error', 'Erreur lors de la cloturation de la reclamation');
            }
        }

        return $this->redirectToRoute('app_reclamation_admin_index', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/supprimer/{idRec}', name: 'app_reclamation_delete', methods: ['POST'])]
    public function delete(Request $request, Reclamation $reclamation, ReclamationRepository $reclamationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reclamation->getIdRec(), $request->request->get('_token'))) {
            try{
                $reclamationRepository->remove($reclamation, true);
                $this->addFlash('success', 'Réclamation Suprimer avec success');
            } catch (\Exception $e){
                $this->addFlash('error', 'Erreur lors de la suppression de la reclamation');
            }
        }

        return $this->redirectToRoute('app_reclamation_admin_index', [], Response::HTTP_SEE_OTHER);
    }
}
