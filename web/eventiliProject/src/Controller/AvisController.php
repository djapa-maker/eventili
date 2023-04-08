<?php

namespace App\Controller;

use App\Entity\Avis;
use App\Form\AvisType;
use App\Repository\AvisRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Repository\ImagePersRepository;
use Knp\Component\Pager\PaginatorInterface;

#[Route('/avis')]
class AvisController extends AbstractController
{
    #[Route('/', name: 'app_avis_index', methods: ['GET'])]
    public function index( PaginatorInterface $paginator,AvisRepository $avisRepository,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        $search=$request->query->get('search');
        if($search){
            $av=$avisRepository->getAvisByPersonName($search);
        }
        else {
            $av = $avisRepository->findAll(); 
        }
        $query = $av;
        $pagination = $paginator->paginate(
        $query,
        $request->query->getInt('page', 1),
        1 // limit per page
    );
        return $this->render('templates_back/avis/index.html.twig', [
            'avis' => $pagination,
            'personne' => $personne,
            'last'=> $last,
            
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_avis_new', methods: ['GET', 'POST'])]
    public function new(Request $request, AvisRepository $avisRepository,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        $avi = new Avis();
        $avisRepository->save($avi, true);

            return $this->redirectToRoute('app_avis_index', [], Response::HTTP_SEE_OTHER);


        return $this->renderForm('templates_back/avis/new.html.twig');
    }
//------------------------------------------------------------------------------------------------
    #[Route('/{idAv}', name: 'app_avis_show', methods: ['GET'])]
    public function show(Avis $avi,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/avis/show.html.twig', [
            'avi' => $avi,
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/{idAv}/edit', name: 'app_avis_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Avis $avi, AvisRepository $avisRepository,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        $form = $this->createForm(AvisType::class, $avi);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $avisRepository->save($avi, true);

            return $this->redirectToRoute('app_avis_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('templates_back/avis/edit.html.twig', [
            'avi' => $avi,
            'form' => $form,
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/{idAv}', name: 'app_avis_delete', methods: ['POST'])]
    public function delete(Request $request, Avis $avi, AvisRepository $avisRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$avi->getIdAv(), $request->request->get('_token'))) {
            $avisRepository->remove($avi, true);
        }

        return $this->redirectToRoute('app_avis_index', [], Response::HTTP_SEE_OTHER);
    }
//------------------------------------------------------------------------------------------------
#[Route('/findAvisById/{idAv}', name: 'app_avis_findAvisById', methods: ['GET'])]
public function findAvisById(AvisRepository $AvisRepository,$idAv,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
    return $this->render('templates_back/avis/index.html.twig', [
        'avis' => $AvisRepository->findby(array('idAv'=>$idAv)),
        'personne' => $personne,
        'last'=> $last,
    ]);
} 
//------------------------------------------------------------------------------------------------
#[Route('/findByIdSousService/{idService}', name: 'app_avis_findByIdSousService', methods: ['GET'])]
public function findByIdSousService(AvisRepository $AvisRepository,$idService,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
    return $this->render('templates_back/avis/index.html.twig', [
        'avis' => $AvisRepository->findby(array('idService'=>$idService)),
        'personne' => $personne,
        'last'=> $last,
    ]);
} 
//------------------------------------------------------------------------------------------------
#[Route('/findByIdP/{pers}', name: 'app_avis_findByIdP', methods: ['GET'])]
public function findByIdP(AvisRepository $AvisRepository,$pers,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
    return $this->render('templates_back/avis/index.html.twig', [
        'avis' => $AvisRepository->findby(array('pers'=>$pers)),
        'personne' => $personne,
        'last'=> $last,
    ]);
}
//------------------------------------------------------------------------------------------------
#[Route('/getAvisByPersonName/{nom}', name: 'app_avis_getAvisByPersonName', methods: ['GET'])]
public function getAvisByPersonName(AvisRepository $AvisRepository,$nom,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
    return $this->render('templates_back/avis/index.html.twig', [
        'avis' => $AvisRepository->getAvisByPersonName($nom),
        'personne' => $personne,
        'last'=> $last,
    ]);
}
}
