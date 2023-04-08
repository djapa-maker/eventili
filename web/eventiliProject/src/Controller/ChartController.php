<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;

class ChartController extends AbstractController
{
    #[Route('/chart', name: 'app_chart')]
    public function index(SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/chart/index.html.twig', [
            'controller_name' => 'ChartController',
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
}
