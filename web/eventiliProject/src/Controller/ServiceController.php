<?php

namespace App\Controller;

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
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;

#[Route('/service')]
class ServiceController extends AbstractController
{
    //constructeur pour la dynamic search-----------------------------------------------------------------------------------------------------------------
    private $tabserv;
    public function __construct(ServiceRepository $serviceRepository)
    {
        $service = $serviceRepository->findAll();
        $servicesArray = array_map(function ($service) {
            return [
                'idService' => $service->getIdService(),
                'nom' => $service->getNom(),
            ];
        }, $service);
        $this->tabserv = $servicesArray;
    }
    //affichage des services---------------------------------------------------------------------------------------    
    #[Route('/', name: 'app_service_index', methods: ['GET', 'POST'])]
    public function index(PaginatorInterface $paginator, ImagePersRepository $imagePersRepository, ServiceRepository $serviceRepository, request $request, SessionInterface $session): Response
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
            'last' => $last,
        ]);
    }
    // recherche dynamic par service ---------------------------------------------------------------------------------------    
    #[Route('/search', name: 'app_service_search')]
    public function search(Request $request): JsonResponse
    {
        $searchTerm = $request->request->get('searchTerm');

        $list = $this->tabserv;

        $results = array_filter($list, function ($item) use ($searchTerm) {
            return stripos($item['nom'], $searchTerm) !== false;
        });

        return $this->json(array_values($results));
    }
    //creation d'un service -------------------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_service_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ServiceRepository $serviceRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
            'last' => $last,
        ]);
    }
    //modification d'un service-------------------------------------------------------------------------------------------------
    #[Route('/{idService}/edit', name: 'app_service_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Service $service, ServiceRepository $serviceRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
            'last' => $last,
        ]);
    }
    //suppression d'un service-------------------------------------------------------------------------------------------------
    #[Route('/{idService}', name: 'app_service_delete', methods: ['POST'])]
    public function delete(Request $request, Service $service, ServiceRepository $serviceRepository): Response
    {

        if ($this->isCsrfTokenValid('delete' . $service->getIdService(), $request->request->get('_token'))) {
            $serviceRepository->remove($service, true);
        }

        return $this->redirectToRoute('app_service_index', [], Response::HTTP_SEE_OTHER);
    }
    //========================================================================================================================================================================
    //========================================================================================================================================================================
    //=========================================MOBILE===============================================================================================================================
    //========================================================================================================================================================================
    //========================================================================================================================================================================

    #[Route('/mobile', name: 'app_service_indexMobile', methods: ['GET', 'POST'])]
    public function indexMobile(PaginatorInterface $paginator, ImagePersRepository $imagePersRepository, ServiceRepository $serviceRepository, request $request, SessionInterface $session, SerializerInterface $serializer): Response
    {

        $service = $serviceRepository->findAll();
        $json = $serializer->serialize($service, 'json', ['groups' => "services"]);
        return new Response($json);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    #[Route('/newMobile', name: 'app_service_newMobile', methods: ['GET', 'POST'])]
    public function newMobile(NormalizerInterface $Normalizer, Request $request, ServiceRepository $serviceRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
    {


        $em = $this->getDoctrine()->getManager();
        $serv = new Service();
        $serv->setNom($request->get('nom'));
        $em->persist($serv);
        $em->flush();

        $jsonContent = $Normalizer->normalize($serv, 'json', ['groups' => 'services']);
        return new Response(json_encode($jsonContent));
    }

    //-----------------------------------------------------------------------------------------------------------------------

    #[Route('/{idService}/editMobile', name: 'app_service_editMobile', methods: ['GET', 'POST'])]
    public function editMobile(NormalizerInterface $Normalizer, Request $request, Service $service, ServiceRepository $serviceRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
    {
        $em = $this->getDoctrine()->getManager();
        $serv = $em->getRepository(Service::class)->find($service);
        $serv->setNom($request->get('nom'));
        $em->flush();

        $jsonContent = $Normalizer->normalize($serv, 'json', ['groups' => 'services']);
        return new Response("Student updated successfully " . json_encode($jsonContent));

    }

    //------------------------------------------------------------------------------------------------------------------------------
    #[Route('/{idService}/deleteMobile', name: 'app_service_deleteMobile')]
    public function deleteMobile(NormalizerInterface $Normalizer,Request $request, Service $service, ServiceRepository $serviceRepository): Response
    {
        $em = $this->getDoctrine()->getManager();
        $serv = $em->getRepository(Service::class)->find($service);
        $em->remove($serv);
        $em->flush();
        $jsonContent = $Normalizer->normalize($serv, 'json', ['groups' => 'services']);
        return new Response("Student deleted successfully " . json_encode($jsonContent));
     
    }
}