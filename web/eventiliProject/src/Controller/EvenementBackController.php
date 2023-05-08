<?php

namespace App\Controller;

use App\Repository\CategEventRepository;
use App\Repository\EvenementRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\ImagePersRepository;
use App\Repository\ImgevRepository;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\HttpFoundation\Request;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Serializer\SerializerInterface;

#[Route('/evenementAdmin')]
class EvenementBackController extends AbstractController
{
    #[Route('/', name: 'app_eventBack_index')]
    public function index(CategEventRepository $CategEventRepository, PaginatorInterface $paginator, Request $request, EvenementRepository $evenementRepository, ImagePersRepository $imagePersRepository, SessionInterface $session, ImgevRepository $imgevRepository): Response
    {
        $imgev = [];
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
        $evenements = $evenementRepository->findAll();
        //----------------------------------------------------------
        foreach ($evenements as $event) {
            $imgev[$event->getIdEv()] = $imgevRepository->findBy(['idEven' => $event->getIdEV()]);
        }
        $categ = $CategEventRepository->findAll();
        $query = $evenements;
        $pagination = $paginator->paginate(
            $query,
            $request->query->getInt('page', 1),
            7 // limit per page
        );
        //dd($imgev);

        return $this->render('templates_back/event/index.html.twig', [
            'personne' => $personne,
            'last' => $last,
            'firstimg' =>  $imgev,
            'events' => $pagination,
            'eventCat' => $categ,
        ]);
    }

    //========================================================================================================================================================================
    //=========================================MOBILE=========================================================================================================================
    //========================================================================================================================================================================

    //affichage des événements---------------------------------------------------------------------------------------  
    #[Route('/MobileEv', name: 'app_eventBack_indexMobile')]
    public function indexMobile(SerializerInterface $serializer, EvenementRepository $evenementRepository): Response
    {
       
        $evenements = $evenementRepository->findAll();
        
        $json = $serializer->serialize($evenements, 'json', ['groups' => "Event"]);
        return new Response($json);
    }  
    
}
