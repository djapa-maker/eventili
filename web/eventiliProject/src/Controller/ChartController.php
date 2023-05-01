<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Repository\ServiceRepository;
use App\Repository\SousserviceRepository;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;
use App\Repository\CategEventRepository;
use App\Repository\EvenementRepository;
//-------------------------------------------------------------------
class ChartController extends AbstractController
{
// affichage -------------------------------------------------------------------    
    #[Route('/chart', name: 'app_chart')]
    public function index(CategEventRepository $categEventRepository,EvenementRepository $evenementRepository,SousserviceRepository $SousserviceRepository, ServiceRepository $ServiceRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
    {
        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        $serv = $ServiceRepository->findAll();
        $data = [
            'labels' => [],
            'datasets' => [
                [
                    'label' => 'nombre de sous service par service ',
                    'data' => [],
                    'backgroundColor' => [],
                ],
            ],
        ];
        foreach ($serv as $s) {
            $data['labels'][] = $s->getNom();
            $data['datasets'][0]['data'][] = count($SousserviceRepository->findByIdService($s));
            $r = rand(150, 255);
            $g = rand(150, 255);
            $b = rand(150, 255);
            $opacity = 0.7;
            $color = "rgba($r, $g, $b, $opacity)";
            $data['datasets'][0]['backgroundColor'][] = $color;
        }
        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');
        //---------------------------------------------------------------
        $categ = $categEventRepository->findAll();
        $data2 = [
            'Labels' => [],
            'datasets' => [
                [
                'label'=> "Nombre des événements par catégorie",
                'data' => [],
                'backgroundColor' => [],
                ],
            ],
        ];
        foreach ($categ as $cat) {
        $data2['labels'][] = $cat->getType();
        $data2['datasets'][0]['data'][] = count($evenementRepository->findByIdCateg($cat));
        $r = rand(150, 255);
            $g = rand(150, 255);
            $b = rand(150, 255);
            $opacity = 0.7;
            $color = "rgba($r, $g, $b, $opacity)";
        $data2['datasets'][0]['backgroundColor'][] = $color;
        }
        return $this->render('templates_back/chart/index.html.twig', [
            'controller_name' => 'ChartController',
            'personne' => $personne,
            'last' => $last,
            'chart_data' => $data,
            'event_chart_data' => $data2,
        ]);
    }
}
