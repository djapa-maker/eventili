<?php

namespace App\Controller;

use App\Entity\Imagepers;
use App\Entity\Personne;
use App\Form\ImagepersType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\TextType as TextType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use App\Repository\ImagePersRepository;
use Doctrine\ORM\EntityManagerInterface;
use phpDocumentor\Reflection\Types\This;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\DataTransformer\CallbackTransformer;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Validator\Constraints\Length;

#[Route('/imagepers')]
class ImagepersController extends AbstractController
{
    #[Route('/', name: 'app_imagepers_index', methods: ['GET'])]
    public function index(ImagePersRepository $imagePersRepository, SessionInterface $session): Response
    {
        $last=$session->get('last');
        $personne=$session->get('id'); 
        $idPerss = $session->get('personne'); 
        if (!$personne) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }else{
        if($personne->getRole()=="admin"){
            return $this->render('templates_back/imagepers/index.html.twig', [
                'imagepers' => $imagePersRepository->findAll(),
                'last'=>$last,
                'personne'=>$personne
            ]);
        }
        else{
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
    }
    }
    #[Route('add', name: 'app_imagepers_add', methods: ['GET', 'POST'])]
    public function add(Request $request, ImagePersRepository $imagePersRepository, SessionInterface $session, EntityManagerInterface $entityManager): Response
    {
        $idPerss = $session->get('personne');
       // $per=$imagePersRepository->find($idPers);
       $personne = $entityManager->getRepository(Personne::class)->find($idPerss);
    // check if the $personne object exists
    if (!$personne) {
        throw $this->createNotFoundException('Personne not found');
    }
        $imageper = new Imagepers();
       
$file=$request->files->get('image');
        
          
              if ($file) {
               
                $fileName=$file instanceof UploadedFile ? $file->getClientOriginalName() : null; 
                $test=$fileName;
            $file->move(
                $this->getParameter('images_directory'),
                $fileName
            );

            // Affecter le nom du fichier à la propriété "imagep" de l'objet $imageper
            $imageper->setImagep($fileName);
            $imageper->setLast($fileName);
            $imageper->setIdPers($personne);

            $imagePersRepository->save($imageper, true);

            return $this->redirectToRoute('app_imagepers_affich', [], Response::HTTP_SEE_OTHER);
        }
            
        if (!$personne) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
        else{
            if($personne->getRole()=="organisateur" ||$personne->getRole()=="partenaire"){
                return $this->render('templates_front/imagepers/signinimage.html.twig', [
                    'imageper' => $imageper,
                ]);
            }
            else {
                return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
            }
        }

       
    }
    
    #[Route('modifier', name: 'app_imagepers_modifier', methods: ['GET', 'POST'])]
    public function modifier(Request $request, ImagePersRepository $imagePersRepository, SessionInterface $session, EntityManagerInterface $entityManager): Response
    {       $idPerss = $session->get('personne');
        $personne = $entityManager->getRepository(Personne::class)->find($idPerss);
        $imagepes = $imagePersRepository->findBy(['idPers' => $idPerss]);
            $images = array_reverse($imagepes);
            if(!empty($images)){
                $i= $images[0];
                $last=$i->getLast();
            }
            else{
                $last="account (1).png";
            }
        if ($request->isMethod('POST') && $request->request->has('submit')) {
            
            
            
            if (!$personne) {
                return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        
            }
    
            $file = $request->files->get('filepond');
            if ($file) {
                $fileName = $file->getClientOriginalName(); 
                $imageper = new Imagepers();
                $imageper->setImagep($fileName);
                $imageper->setLast($fileName);
                $imageper->setIdPers($personne);
                $imagePersRepository->save($imageper, true);
                foreach ($images as $imagepe) {
                    $imagepe->setLast($imageper->getImagep()); 
                    $imagePersRepository->save($imagepe, true);
                }
                return $this->redirectToRoute('app_imagepers_affich', [], Response::HTTP_SEE_OTHER);
            }
        }
       
            if($personne->getRole()=="organisateur" ||$personne->getRole()=="partenaire"){
                return $this->renderForm('templates_front/imagepers/modifimage.html.twig', [
                    'personne' => $personne,
                    'last' =>$last,
                ]);
            }
            else {
                return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
            }
        
        
    }
    
    #[Route('new', name: 'app_imagepers_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ImagePersRepository $imagePersRepository, SessionInterface $session, EntityManagerInterface $entityManager): Response
    {
        $personne1=$session->get('id'); 
        $idPers = $session->get('personne'); 
        $images = $imagePersRepository->findBy(['idPers' => $idPers]);
        $images = array_reverse($images);
        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }

        $idPerss = $session->get('idPer');
       // $per=$imagePersRepository->find($idPers);
       $personne = $entityManager->getRepository(Personne::class)->find($idPerss);
    // check if the $personne object exists
    if (!$personne) {
        throw $this->createNotFoundException('Personne not found');
    }
        var_dump($idPerss);
        $imageper = new Imagepers();
        $form = $this->createFormBuilder($imageper)
        ->add('imagep', FileType::class, [
            'label' => 'Image',
            'attr' => [
                'accept' => 'image/*', // accepter uniquement les fichiers d'images
                'onchange' => 'previewImage(this)', // ajouter une méthode pour prévisualiser l'image sélectionnée
            ],
            'label_attr' => ['hidden' => true]
        ])
        ->add('last', TextType::class,[
            'data'=> "a",
        'attr' => ['hidden' => true],
       'label_attr' => ['hidden' => true]])
        ->add('idPers', EntityType::class, [
            'class' => Personne::class,
            'data' => $personne,
            'multiple' => false,
            'expanded' => false,
            'attr' => ['hidden' => true],
           'label_attr' => ['hidden' => true]
        ])
        ->getForm();
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('imagep')->getData();
              if ($file) {
            
            $fileName = $file->getClientOriginalName();
           // Générer un nom unique pour le fichier
            // $fileName = uniqid().'.'.$file->guessExtension();

            // Déplacer le fichier vers le dossier d'images
            $file->move(
                $this->getParameter('images_directory'),
                $fileName
            );

            // Affecter le nom du fichier à la propriété "imagep" de l'objet $imageper
            $imageper->setImagep($fileName);
            $imageper->setLast($fileName);
        }
           // $imageper->setLast($form->get('imagep')->getData());
            $imagePersRepository->save($imageper, true);

            return $this->redirectToRoute('app_imagepers_index', [], Response::HTTP_SEE_OTHER);
        }
        if (!$personne) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }else{
        if($personne1->getRole()=="admin"){
            return $this->renderForm('templates_back/imagepers/new.html.twig', [
                'imageper' => $imageper,
                'form' => $form,
                'last'=>$last,
                'personne'=>$personne1,
            ]);
        }
        else{
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
    }
    }

    #[Route('/{idImp}', name: 'app_imagepers_show', methods: ['GET'])]
    public function show(Imagepers $imageper): Response
    {
        return $this->render('templates_back/imagepers/show.html.twig', [
            'imageper' => $imageper,
        ]);
    }
    #[Route('affich', name: 'app_imagepers_affich', methods: ['GET'])]
    public function affich(ImagePersRepository $imagePersRepository, SessionInterface $session): Response
    {
        $images = new Imagepers();
        $idPerss = $session->get('personne'); 
        $personne = $session->get('id'); 
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }
        if (!$personne) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
        else{
        if($personne->getRole()=="organisateur" ||$personne->getRole()=="partenaire"){
            return $this->render('templates_front/personne/profil.html.twig', [
                'images' => $images,
                'last' => $last,
                'personne' => $personne,
            ]);
        }
        else if($personne->getRole()=="admin"){
            return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
        }}

       
    }
    #[Route('/{idImp}/edit', name: 'app_imagepers_edit', methods: ['GET', 'POST'])]
public function edit(Request $request, Imagepers $imageper, ImagePersRepository $imagePersRepository): Response
{
   
   
   
    $form = $this->createFormBuilder($imageper)
    
    ->add('imagep', FileType::class, [
        'data' => $imageper->getImagep(),
        'attr' => [
            'accept' => 'image/*', // accepter uniquement les fichiers d'images
            'onchange' => 'previewImage(this)', // ajouter une méthode pour prévisualiser l'image sélectionnée
        ],
        'label_attr' => ['hidden' => true]
    ])
        ->add('last', TextType::class,[
        'attr' => ['hidden' => true],
       'label_attr' => ['hidden' => true]])
       
        ->add('save', SubmitType::class, ['label' => 'Enregistrer'])
        ->getForm();

    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $imagePersRepository->save($imageper, true);

        return $this->redirectToRoute('app_imagepers_index', [], Response::HTTP_SEE_OTHER);
    }

    return $this->renderForm('templates_back/imagepers/edit.html.twig', [
        'imageper' => $imageper,
        'form' => $form,
    ]);
}
#[Route('/{idImp}', name: 'app_imagepers_delete1', methods: ['POST'])]
    public function delete1(Request $request, Imagepers $imageper, ImagePersRepository $imagePersRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$imageper->getIdImp(), $request->request->get('_token'))) {
            $imagePersRepository->remove($imageper, true);
        }

        return $this->redirectToRoute('app_imagepers_affich', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/{idImp}', name: 'app_imagepers_delete', methods: ['POST'])]
    public function delete(Request $request, Imagepers $imageper, ImagePersRepository $imagePersRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$imageper->getIdImp(), $request->request->get('_token'))) {
            $imagePersRepository->remove($imageper, true);
        }

        return $this->redirectToRoute('app_imagepers_index', [], Response::HTTP_SEE_OTHER);
    }
}







