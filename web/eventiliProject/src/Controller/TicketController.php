<?php

namespace App\Controller;

use App\Entity\Ticket;
use App\Form\TicketType;

use App\Repository\EvenementRepository;
use App\Repository\TicketRepository;

use Symfony\Component\HttpFoundation\Request;


use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\HttpFoundation\JsonResponse;



#[Route('/ticket')]
class TicketController extends AbstractController
{
    private $tabserv;
    public function __construct(TicketRepository $ticketRepository)
    {
        $tikets=$ticketRepository->findAll();
        $tiketsArray = array_map(function ($tikets) {
            return [
                'idTick' => $tikets->getIdTick(),
                'status' => $tikets->getStatus(),
            ];
        }, $tikets);
        $this->tabserv =$tiketsArray;
    }

    /*#[Route('/statistic', name: 'app_ticket_statistic_index', methods: ['GET'])]
    public function statistiques(EvenementRepository $evenementRepository, TicketRepository $ticketRepository ){
        // On va chercher toutes les catégories
        
        $tickets = $ticketRepository->findAll();

        $idticket = [];       
        $idevent = [];

        // On "démonte" les données pour les séparer tel qu'attendu par ChartJS
        foreach($tickets as $ticket){
            $tickets[] = $ticket->getIdTick();
           // $formColor[] = $categorie->getColor();
           // $idevent[] = count($ticket->getIdevent());
    
            }


        

        return $this->render('templates_back/ticket/statistic.html.twig', [
            'tickets' => json_encode($tickets),
            //'categColor' => json_encode($categColor),
            'idevent' => json_encode($idevent),
            

        ]);
    } 
*/
    #[Route('/statistic',name: 'app_ticket_statistic_index')]
public function statAdmin(Request $request,TicketRepository $repo){
    $nboffre[]=Array();




    $e2=$repo->findByStatuss("Active");
    dump($e2);
    $nboffre[]=$e2[0][1];

    $e3=$repo->findByStatuss("Désactive");
    dump($e3);
    $nboffre[]=$e3[0][1];

    dump($nboffre);
    reset($nboffre);
    dump(reset($nboffre));
    $key = key($nboffre);
    dump($key);
    dump($nboffre[$key]);

    unset($nboffre[$key]);

    $nbrss=array_values($nboffre);
    dump(json_encode($nbrss));

return $this->render('templates_back/ticket/statistic.html.twig',[
    'nboffre' => json_encode($nbrss),
 
 

]);
}
#[Route('/', name: 'app_ticket_index', methods: ['GET','POST'])]
public function index(EntityManagerInterface $entityManager,Request $request, TicketRepository $ticketRepository): Response
{ $tickets = $entityManager
    ->getRepository(Ticket::class)
    ->findAll();
      /////////
      $back = null;
        
      if($request->isMethod("POST")){
          if ( $request->request->get('optionsRadios')){
              $SortKey = $request->request->get('optionsRadios');
              switch ($SortKey){
                  case 'id':
                      $tickets = $ticketRepository->sortById();
                      break;

                  case 'status':
                      $tickets = $ticketRepository->SortBystatus();
                      break;

              }
          }
          else
          {
              $type = $request->request->get('optionsearch');
              $value = $request->request->get('Search');
              switch ($type){
                  case 'status':
                      $tickets = $ticketRepository->findByStatus($value);
                      break;

                  case 'nbtick':
                      $tickets = $ticketRepository->findByNbTick($value);
                      break;

                      case 'prixTick':
                        $tickets = $ticketRepository->findByPrixTick($value);
                        break;    

              }
          }

          if ( $tickets){
              $back = "success";
          }else{
              $back = "failure";
          }
      }
      return $this->render('templates_back/ticket/index.html.twig', [
        'tickets' => $tickets,
    ]);
}


    
  /*      #[Route('/', name: 'app_ticket_index', methods: ['GET'])]
        public function index(EntityManagerInterface $entityManager, TicketRepository $ticketRepository, request $request): Response
        {
            $tickets = $entityManager
                ->getRepository(Ticket::class)
                ->findAll();

                $search = $request->query->get('search');
                if ($search) {
                    $tickets = $ticketRepository->findOneBystat($search);
                } else {
                    $tickets = $ticketRepository->findAll();
                }    
    
            return $this->render('templates_back/ticket/index.html.twig', [
                'tickets' => $tickets,
            ]);
        }
/********************************************************************************************* 
#[Route('/search', name: 'app_ticket_search')]
public function search(Request $request): JsonResponse
{
    $searchTerm = $request->request->get('searchTerm');

    $list = $this->tabserv;
    
    $results = array_filter($list, function ($item) use ($searchTerm) {
        return stripos($item['status'], $searchTerm) !== false;
    });

    return $this->json(array_values($results));
}


*/




/******************************************************************************************** */
        #[Route('/new', name: 'app_ticket_new', methods: ['GET', 'POST'])]
        public function new(Request $request, EntityManagerInterface $entityManager): Response
        {
            $ticket = new Ticket();
            $form = $this->createForm(TicketType::class, $ticket);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $entityManager->persist($ticket);
                $entityManager->flush();
    
                return $this->redirectToRoute('app_ticket_index', [], Response::HTTP_SEE_OTHER);
            }
    
            return $this->renderForm('templates_back/ticket/new.html.twig', [
                'ticket' => $ticket,
                'form' => $form,
            ]);
        }
    
        #[Route('/{idTick}', name: 'app_ticket_show', methods: ['GET'])]
        public function show(Ticket $ticket): Response
        {
            return $this->render('templates_back/ticket/show.html.twig', [
                'ticket' => $ticket,
            ]);
        }
    
        #[Route('/{idTick}/edit', name: 'app_ticket_edit', methods: ['GET', 'POST'])]
        public function edit(Request $request, Ticket $ticket, EntityManagerInterface $entityManager): Response
        {
            $form = $this->createForm(TicketType::class, $ticket);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $entityManager->flush();
    
                return $this->redirectToRoute('app_ticket_index', [], Response::HTTP_SEE_OTHER);
            }
    
            return $this->renderForm('templates_back/ticket/edit.html.twig', [
                'ticket' => $ticket,
                'form' => $form,
            ]);
        }
    
        #[Route('/{idTick}', name: 'app_ticket_delete', methods: ['POST'])]
        public function delete(Request $request, Ticket $ticket, EntityManagerInterface $entityManager): Response
        {
            if ($this->isCsrfTokenValid('delete'.$ticket->getIdTick(), $request->request->get('_token'))) {
                $entityManager->remove($ticket);
                $entityManager->flush();
            }
    
            return $this->redirectToRoute('app_ticket_index', [], Response::HTTP_SEE_OTHER);
        }




/*///////////////////////////////////////////////////////////////////////////////////////////////////*/

#[Route('/findByStatus/{status}', name: 'app_ticket_findByStatus', methods: ['GET'])]
public function findOneBystat(TicketRepository $ticketRepository, $status): Response
{
   

    return $this->render('templates_back/ticket/index.html.twig', [
        'tickets' =>  $ticketRepository->findOneBystat($status),
    
        
    ]);
}
//------------------------------------------------------------------------------------------------- 
#[Route('/findByStatuss/{status}', name: 'app_ticket_findByStatuss', methods: ['GET'])]
public function findOneBystats(TicketRepository $ticketRepository, $status ): Response
{
    

    
    return $this->render('templates_back/service/index.html.twig', [
        'tickets' => $ticketRepository->findOneBystats($status),
       
    ]);
}



#[Route('/findbyId/{idTick}', name: 'app_ticket_findById', methods: ['GET'])]
    public function FindServiceById(TicketRepository $ticketRepository, $idService): Response
    {

        return $this->render('templates_back/ticket/index.html.twig', [
            'tickets' => $ticketRepository->findby(array('idService' => $idService)),
          
        ]);
    }

/******************************************* */




        
}
