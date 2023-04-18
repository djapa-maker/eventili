<?php

namespace App\Controller;

use App\Entity\Personne;
use App\Form\PersonneType;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;
use Doctrine\ORM\EntityManagerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\RouterInterface;

#[Route('/personne')]
class PersonneController extends AbstractController
{
   

    #[Route('/index', name: 'app_personne_index', methods: ['GET'])]
    public function index( Request $request,ImagePersRepository $imagePersRepository,PersonneRepository $personneRepository,SessionInterface $session ): Response
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

        $search = $request->query->get('search1');
        $filter = null;
        $filter = $request->query->get('inputfilter');
        if ($filter) {
            $personnes = $personneRepository->getAllByPersonneRole($filter);
        } elseif ($search) {
            $personnes = $personneRepository->findOneByName($search);
        } else {
            $personnes = $personneRepository->findAll();
        }
        if (!$personne) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
        else{
            if($personne->getRole()=="admin"){
                return $this->render('templates_back/personne/index.html.twig', [
                    'personnes' => $personnes,
                    'personne' => $personne,
                    'last'=> $last,
                ]);
            }
            else{
                return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
            }
        }
       
        
    }
    #[Route('/', name: 'app_personne_accueil', methods: ['GET'])]
    public function acceuil(): Response
    {
        return $this->render('templates_front/personne/accueil.html.twig');
    }
    #[Route('/signin', name: 'app_personne_signin', methods: ['GET', 'POST'])]
    public function signin(Request $request, PersonneRepository $personneRepository,SessionInterface $session ): Response
    {
        $personne = new Personne();
        $form = $this->createForm(PersonneType::class, $personne);
        $form->handleRequest($request);
       
        if ($form->isSubmitted() && $form->isValid()) {
            $personneRepository->save($personne, true);
            $session->set('id', $personne);
            $session->set('personne', $personne->getIdPers());
            return $this->redirectToRoute('app_imagepers_add', [], Response::HTTP_SEE_OTHER);
        }
    
        return $this->renderForm('templates_front/personne/signin.html.twig', [
            'personne' => $personne,
            'form' => $form,
        ]);
    }

    
    #[Route('/editprofil', name: 'app_personne_editprofil', methods: ['GET', 'POST'])]
    public function editprofil(Request $request,ImagePersRepository $imagePersRepository,PersonneRepository $personneRepository,SessionInterface $session ): Response
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

        $form = $this->createForm(PersonneType::class, $personne);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $personneRepository->modifyPersonne($idPerss,$personne);
            return $this->redirectToRoute('app_personne_change', [], Response::HTTP_SEE_OTHER);
            
        }

        if (!$personne) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
        else{
            if($personne->getRole()=="organisateur" || $personne->getRole()=="partenaire"){
                return $this->renderForm('templates_front/personne/editp.html.twig', [
                    'personne' => $personne,
                    'form' => $form,
                    'last' => $last,
                ]);
            }
            else{
                return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
            }
        }
        

    }
    #[Route('/changep', name: 'app_personne_change', methods: ['GET', 'POST'])]
    public function change(Request $request,ImagePersRepository $imagePersRepository,PersonneRepository $personneRepository, SessionInterface $session): Response
    {  $user=$session->get('id'); 
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
        $mdp1 = $request->request->get('new_password');
        
        $user1=$personneRepository->findemm($user->getEmail(),$user->getMdp());
        if (!empty($mdp1)) {
        if($user1!=null){
            
            $user->setMdp($mdp1);
            $user1->setMdp($mdp1);
            $session->set('id', $user1);
            $personneRepository->update($user, true);
            
       
            return $this->redirectToRoute('app_imagepers_modifier', [], Response::HTTP_SEE_OTHER);
           
        }
    }
    if (!$user) {
        return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
    }
    else{
        if($user->getRole()=="organisateur" || $user->getRole()=="partenaire"){
            return $this->render('templates_Front/personne/change.html.twig', [
                'personne' => $user,
                 'last'=>$last,
            ]);
        }
        else{
            return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
        }
    }
           
    }
    #[Route('/deconnection', name: 'app_personne_deconnection', methods: ['GET', 'POST'])]
    public function deconnection(SessionInterface $session): Response
    {
        $session->remove('idPer');
        $session->remove('personne');
         $session->clear();
        return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/verif', name: 'app_personne_verif', methods: ['GET', 'POST'])]
    public function verif(Request $request, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
        $mdp = $request->request->get('mdp');
        $email = $request->request->get('email');
        $personne=$personneRepository->findemm($email,$mdp);
        if($personne!=null){
            $session->set('id', $personne);
            $session->set('personne', $personne->getIdPers());
            if($personne->getRole()!="admin")
            return $this->redirectToRoute('app_imagepers_affich', [], Response::HTTP_SEE_OTHER);
            else
            return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
           
        }
        $warningMessage = "email ou  mot de passe incorrect";
        return $this->renderForm('templates_front/personne/login.html.twig', [
            'warning_message' => $warningMessage, 
        ]);
    }
    
    

    #[Route('/new', name: 'app_personne_new', methods: ['GET', 'POST'])]
    public function new(Request $request,ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {$user=$session->get('id'); 
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

        $personne = new Personne();
        $form = $this->createForm(PersonneType::class, $personne);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $personneRepository->save($personne, true);
            $idPers = $personne->getIdPers();
        $session->set('idPer', $idPers);
       // $imageUploadUrl = $router->generate('app_imagepers_new', ['idPers' => $personne->getIdPers()]);
        return $this->redirectToRoute('app_imagepers_new', [], Response::HTTP_SEE_OTHER);
        }

        if (!$user) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
        else{
            if($user->getRole()=="admin"){
                return $this->renderForm('templates_back/personne/new.html.twig', [
                    'personne' => $user,
                    'form' => $form,
                    'last'=> $last,
                ]);
            }
            else{
                return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
            }
        }
        
    }

    #[Route('/{idPers}', name: 'app_personne_show', methods: ['GET'])]
    public function show(Personne $personne,ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
        $personne1=$session->get('id'); 
        $idPers = $session->get('personne'); 
        $images = $imagePersRepository->findBy(['idPers' => $idPers]);
        $images = array_reverse($images);
        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }
        if($personne1->getRole()=="admin"){
            return $this->render('templates_back/personne/show.html.twig', [
                'personne' => $personne,
                'last'=>$last,
            ]);
        }
        else{
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
       
    }

    #[Route('/{idPers}/edit', name: 'app_personne_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Personne $personne, ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
        $personne1=$session->get('id'); 
        $idPers = $session->get('personne'); 
        $images = $imagePersRepository->findBy(['idPers' => $idPers]);
        $images = array_reverse($images);
        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }

        $form = $this->createForm(PersonneType::class, $personne);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $personneRepository->save($personne, true);

            return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
        }
        if (!$personne1) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
        else{
            if($personne1->getRole()=="admin"){
                return $this->renderForm('templates_back/personne/edit.html.twig', [
                    'personne' => $personne1,
                    'personne1' => $personne,
                    'form' => $form,
                    'last'=>$last,
                ]);
            }
            else{
                return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
            }
        }
        
        
    }

    #[Route('/{idPers}', name: 'app_personne_delete', methods: ['POST'])]
    public function delete(Request $request, Personne $personne, PersonneRepository $personneRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$personne->getIdPers(), $request->request->get('_token'))) {
            $personneRepository->remove($personne, true);
        }

        return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
    }


    
}
