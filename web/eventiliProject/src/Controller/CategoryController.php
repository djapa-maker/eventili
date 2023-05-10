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
use Symfony\Component\Serializer\SerializerInterface;

#[Route('/category')]
class CategoryController extends AbstractController
{

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
    //modification d'une catégorie-------------------------------------------------------------------------------------------------
    #[Route('/{idCateg}/edit', name: 'app_categorie_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, CategEvent $categorie, CategEventRepository $categEventRepository,  SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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

    //========================================================================================================================================================================
    //=========================================MOBILE=========================================================================================================================
    //========================================================================================================================================================================

    //affichage des catégories---------------------------------------------------------------------------------------    
    #[Route('/mobile', name: 'app_categorie_indexMobile', methods: ['GET', 'POST'])]
    public function indexMobile(SerializerInterface $serializer, CategEventRepository $categEventRepository): Response
    {

        $categorie = $categEventRepository->findAll();
        $json = $serializer->serialize($categorie, 'json', ['groups' => "CategEventC"]);
        return new Response($json);
    }

    //creation d'une catégorie -------------------------------------------------------------------------------------------------
    #[Route('/mobile/new', name: 'app_categorie_newMobile', methods: ['GET', 'POST'])]
    public function newMobile(SerializerInterface $serializer, Request $request, CategEventRepository $categEventRepository): Response
    {
        $categ = new CategEvent();
        $categ->setType($request->get('type'));
        $categEventRepository->save($categ, true);

        $json = $serializer->serialize($categ, 'json', ['groups' => "CategEventC"]);
        return new Response($json);
    }
    //http://127.0.0.1:8000/category/mobile/new?type=coucou
    //suppression d'une catégorie-------------------------------------------------------------------------------------------------
    #[Route('/mobile/{idCateg}', name: 'app_categorie_deleteMobile')]
    public function deleteMobile(SerializerInterface $serializer, CategEvent $categorie, CategEventRepository $categEventRepository): Response
    {

        $categ = $categEventRepository->find($categorie);
        $categEventRepository->remove($categ, true);


        $json = $serializer->serialize($categ, 'json', ['groups' => "CategEventC"]);
        return new Response($json);
    }
    //http://127.0.0.1:8000/category/mobile/34

    //modification d'une catégorie-------------------------------------------------------------------------------------------------
    #[Route('/mobile/edit/{idCateg}', name: 'app_categorie_editMobile', methods: ['GET', 'POST'])]
    public function editMobile(SerializerInterface $serializer,Request $request, CategEvent $categorie, CategEventRepository $categEventRepository): Response
    {
        
            $categ = $categEventRepository->find($categorie);
            $categ->setType($request->get('type'));
            $categEventRepository->save($categ, true);

            $json = $serializer->serialize($categ, 'json', ['groups' => "CategEventC"]);
            return new Response($json);
    }
    //http://127.0.0.1:8000/category/mobile/edit/32?type=camp

}
