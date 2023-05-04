<?php

namespace App\Controller;

use App\Entity\CategEvent;
use App\Form\CategorieType;
use App\Repository\CategEventRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Knp\Component\Pager\PaginatorInterface;
use App\Repository\ImagePersRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;

#[Route('/category')]
class CategoryController extends AbstractController
{
    //constructeur pour la dynamic search-----------------------------------------------------------------------------------------------------------------
    // private $tabserv;
    // public function __construct(CategEventRepository $categEventRepository)
    // {
    //     $categorie=$categEventRepository->findAll();
    //     $categoriesArray = array_map(function ($categorie) {
    //         return [
    //             'idCateg' => $categorie->getIdCateg(),
    //             'type' => $categorie->getType(),
    //         ];
    //     }, $categorie);
    //     $this->tabserv =$categoriesArray;
    // }
    
    //affichage des catégories---------------------------------------------------------------------------------------    
    #[Route('/', name: 'app_categorie_index', methods: ['GET', 'POST'])]
    public function index(PaginatorInterface $paginator, ImagePersRepository $imagePersRepository, CategEventRepository $categEventRepository, request $request, SessionInterface $session): Response
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

        //----------------------------------------
        $categorie = $categEventRepository->findAll();
        //----------------------------------------

        $query = $categorie;
        $pagination = $paginator->paginate(
            $query,
            $request->query->getInt('page', 1),
            7 // limit per page
        );
        return $this->renderForm('templates_back/categorie/index.html.twig', [
            'categories' => $pagination,
            'personne' => $personne,
            'last' => $last,
        ]);
    }

     //creation d'une catégorie -------------------------------------------------------------------------------------------------
     #[Route('/new', name: 'app_categorie_new', methods: ['GET', 'POST'])]
     public function new(Request $request, CategEventRepository $categEventRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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

         //----------------------------------------

         $categ = new CategEvent();
         $form = $this->createForm(CategorieType::class, $categ);
         $form->handleRequest($request);
 
         if ($form->isSubmitted() && $form->isValid()) {
             $categEventRepository->save($categ, true);
             return $this->redirectToRoute('app_categorie_index', [], Response::HTTP_SEE_OTHER);
         }
 
         return $this->renderForm('templates_back/categorie/new.html.twig', [
            'categorie' => $categ,
             'form' => $form,
             'personne' => $personne,
             'last' => $last,
         ]);
     }

     //suppression d'une catégorie-------------------------------------------------------------------------------------------------
    #[Route('/{idCateg}', name: 'app_categorie_delete', methods: ['POST'])]
    public function delete(Request $request, CategEvent $categorie, CategEventRepository $categEventRepository): Response
    {

        if ($this->isCsrfTokenValid('delete' . $categorie->getIdCateg(), $request->request->get('_token'))) {
            $categEventRepository->remove($categorie, true);
        }

        return $this->redirectToRoute('app_categorie_index', [], Response::HTTP_SEE_OTHER);
    }
    // recherche dynamic par catégorie ---------------------------------------------------------------------------------------    
    // #[Route('/search', name: 'app_categorie_search')]
    // public function search(Request $request): JsonResponse
    // {
    //     $searchTerm = $request->request->get('searchTerm');

    //     $list = $this->tabserv;
        
    //     $results = array_filter($list, function ($item) use ($searchTerm) {
    //         return stripos($item['type'], $searchTerm) !== false;
    //     });

    //     return $this->json(array_values($results));
    // }
    //modification d'une catégorie-------------------------------------------------------------------------------------------------
    #[Route('/{idCateg}/edit', name: 'app_categorie_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request,CategEvent $categorie, CategEventRepository $categEventRepository,  SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
        //----------------------------------------
        
        $form = $this->createForm(CategorieType::class, $categorie);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $categEventRepository->save($categorie, true);
            return $this->redirectToRoute('app_categorie_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('templates_back/categorie/edit.html.twig', [
            'categorie' => $categorie,
            'form' => $form,
            'personne' => $personne,
            'last' => $last,
        ]);
    }
}
