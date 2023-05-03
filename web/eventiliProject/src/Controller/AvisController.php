<?php

namespace App\Controller;
//---------------------------------------------------------------------------------------
use App\Entity\Avis;
use App\Form\AvisType;
use App\Repository\AvisRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;
use App\Repository\SousserviceRepository;
use Knp\Component\Pager\PaginatorInterface;
use App\Entity\Sousservice;
use DateTime;
//---------------------------------------------------------------------------------------
#[Route('/avis')]
class AvisController extends AbstractController
{
//affichage ---------------------------------------------------------------------------------------    
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
        7 // limit per page
    );
        return $this->render('templates_back/avis/index.html.twig', [
            'avis' => $pagination,
            'personne' => $personne,
            'last'=> $last,
            
        ]);
    }
//creation ------------------------------------------------------------------------------------------------
    #[Route('/new/{idService}', name: 'app_avis_new', methods: ['GET', 'POST'])]
    public function new(SousserviceRepository $SousserviceRepository,PersonneRepository $PersonneRepository,Request $request, $idService, AvisRepository $avisRepository,SessionInterface $session,ImagePersRepository $imagePersRepository): Response
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
        $avi=new Avis();
        $form = $this->createForm(AvisType::class, $avi);
        $form->handleRequest($request);
        $a=$SousserviceRepository->findOneById($idService);
        
        $avi->setIdService($a);
        $avi->setPers($PersonneRepository->findOneByIdPers($idPerss));
        $avi->setDate(new DateTime());
        if ($form->isSubmitted() && $form->isValid()) {
            $avisRepository->save($avi, true);
            $AviSS = $avisRepository->findByIdService($idService);
            $ToutAvis = $avisRepository->findAll();
            $ratingsum = 0;
            foreach ($AviSS as $a) {
                $ratingsum += $a->getRating();
                $res = $ratingsum / count($ToutAvis);
                $ss = $SousserviceRepository->findOneById($idService);
                $ss->setNote($res);
                $SousserviceRepository->save($ss,true);
            }

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('templates_front/avis_front/new.html.twig', [
            'avi' => $avi,
            'form' => $form,
            'personne' => $personne,
            'last'=> $last,
        ]);
    }
// modification------------------------------------------------------------------------------------------------
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
// suppression ------------------------------------------------------------------------------------------------
    #[Route('/{idAv}', name: 'app_avis_delete', methods: ['POST'])]
    public function delete(Request $request, Avis $avi, AvisRepository $avisRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$avi->getIdAv(), $request->request->get('_token'))) {
            $avisRepository->remove($avi, true);
        }

        return $this->redirectToRoute('app_avis_index', [], Response::HTTP_SEE_OTHER);
    }
}
