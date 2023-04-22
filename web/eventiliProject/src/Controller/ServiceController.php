<?php

namespace App\Controller;
//---------------------------------------------------------------------------------------
use App\Entity\Service;
use App\Form\ServiceType;
use App\Repository\ImagePersRepository;
use App\Repository\ServiceRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\PersonneRepository;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
//---------------------------------------------------------------------------------------
#[Route('/service')]
class ServiceController extends AbstractController
{
//---------------------------------------------------------------------------------------    
    #[Route('/', name: 'app_service_index', methods: ['GET', 'POST'])]
    public function index(  PaginatorInterface $paginator,ImagePersRepository $imagePersRepository,ServiceRepository $serviceRepository, request $request,SessionInterface $session): Response
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

        $search = $request->query->get('search');
        if ($search) {
            $service = $serviceRepository->findOneByName($search);
        } else {
            $service = $serviceRepository->findAll();
        }
        $serv = new Service();
        $form = $this->createForm(ServiceType::class, $serv);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $serviceRepository->save($serv, true);
            return $this->redirectToRoute('app_service_index', [], Response::HTTP_SEE_OTHER);
        }
        $query = $service;
        $pagination = $paginator->paginate(
        $query,
        $request->query->getInt('page', 1),
        7 // limit per page
    );
        return $this->renderForm('templates_back/service/index.html.twig', [
            'service' => $serv,
            'services' => $pagination,
            'form' => $form,
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
//---------------------------------------------------------------------------------------    
    #[Route('/search', name: 'app_service_search', methods: ['POST'])]
    public function search(ServiceRepository $serviceRepository, Request $request): JsonResponse
    {
        $search = $request->request->get('search');
    
        $services = $serviceRepository->findOneByName($search);
    
        $html = $this->renderView('templates_back/service/index.html.twig', [
            'services' => $services
        ]);
    
        return new JsonResponse(['html' => $html]);
    }
//-------------------------------------------------------------------------------------------------
    // #[Route('/', name: 'app_service_index', methods: ['GET', 'POST'])]
    // public function index(  PaginatorInterface $paginator,ImagePersRepository $imagePersRepository,ServiceRepository $serviceRepository, request $request,SessionInterface $session): Response
    // {
    //     $personne=$session->get('id'); 
    //     $idPerss = $session->get('personne'); 
    //     $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
    //     $images = array_reverse($images);
    //     if(!empty($images)){
    //         $i= $images[0];
    //         $last=$i->getLast();
    //     }
    //     else{
    //         $last="account (1).png";
    //     }
    //     $session->set('last', $last);
    //     $last=$session->get('last');
    //     $list=$serviceRepository->findAll();
    //     $search = $request->query->get('search');
        
    //     $query = $list;
    //     $pagination = $paginator->paginate(
    //     $query,
    //     $request->query->getInt('page', 1),
    //     6 // limit per page
    // );
    //     return $this->render('templates_back/service/index.html.twig', [
    //         'list'=>$list,
    //         'services' => $pagination,
    //         'personne' => $personne,
    //         'last'=> $last,
    //     ]);
    // }
//-------------------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_service_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ServiceRepository $serviceRepository,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        $serv = new Service();
        $form = $this->createForm(ServiceType::class, $serv);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            
            $serviceRepository->save($serv, true);

            return $this->redirectToRoute('app_service_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('templates_back/service/new.html.twig', [
            'service' => $serv,
            'form' => $form,
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
//-------------------------------------------------------------------------------------------------
    #[Route('/{idService}', name: 'app_service_show', methods: ['GET'])]
    public function show(Service $service,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/service/show.html.twig', [
            'service' => $service,
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
//-------------------------------------------------------------------------------------------------
    #[Route('/{idService}/edit', name: 'app_service_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Service $service, ServiceRepository $serviceRepository,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        $form = $this->createForm(ServiceType::class, $service);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $serviceRepository->save($service, true);
            return $this->redirectToRoute('app_service_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('templates_back/service/edit.html.twig', [
            'service' => $service,
            'form' => $form,
            'personne' => $personne,
            'last'=> $last,
        ]);
        
    }
//-------------------------------------------------------------------------------------------------
    // #[Route('/{idService}/editt', name: 'app_service_edit1')]
    // public function edit1(Request $request, Service $service, ServiceRepository $serviceRepository): Response
    // {
    //     $var = $request->get('inputService');
    //     $service->setNom($var);
    //     // $form = $this->createForm(ServiceType::class, $service);
    //     // $form->handleRequest($request);

    //     // if ($form->isSubmitted() && $form->isValid()) {
    //     $serviceRepository->save($service, true);

    //     return $this->redirectToRoute('app_service_index', [], Response::HTTP_SEE_OTHER);
    //     // }

    //     return $this->renderForm('service/edit.html.twig', [
    //         // 'service' => $service,
    //         // 'form' => $form,
    //     ]);
    // }
//-------------------------------------------------------------------------------------------------
    #[Route('/{idService}', name: 'app_service_delete', methods: ['POST'])]
    public function delete(Request $request, Service $service, ServiceRepository $serviceRepository): Response
    {
        
        if ($this->isCsrfTokenValid('delete' . $service->getIdService(), $request->request->get('_token'))) {
            $serviceRepository->remove($service, true);
        }

        return $this->redirectToRoute('app_service_index', [], Response::HTTP_SEE_OTHER);
    }
//-------------------------------------------------------------------------------------------------
    #[Route('/findbyId/{idService}', name: 'app_service_findById', methods: ['GET'])]
    public function FindServiceById(ServiceRepository $serviceRepository, $idService,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/service/index.html.twig', [
            'services' => $serviceRepository->findby(array('idService' => $idService)),
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
//-------------------------------------------------------------------------------------------------    
    #[Route('/findbyName/{nom}', name: 'app_service_findByName', methods: ['GET'])]
    public function findByName(ServiceRepository $serviceRepository, $nom,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/service/index.html.twig', [
            'services' =>  $serviceRepository->findOneByName($nom),
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
//------------------------------------------------------------------------------------------------- 
    #[Route('/findbyNames/{nom}', name: 'app_service_findByNames', methods: ['GET'])]
    public function findByNames(ServiceRepository $serviceRepository, $nom,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/service/index.html.twig', [
            'services' => $serviceRepository->findOneByNames($nom),
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
//---------------------------------------------------------------------------------------    
}
