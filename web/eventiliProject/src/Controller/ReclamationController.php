<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Entity\Reponse;
use App\Entity\Personne;
use App\Form\ReclamationAjouterType;
use App\Form\ReclamationRechercheDynamiqueType;
use App\Form\ReclamationType;
use App\Form\ReponseModifierType;
use App\Form\ReponseType;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;
use App\Repository\ReclamationRepository;
use App\Repository\ReponseRepository;
use Karser\Recaptcha3Bundle\Validator\Constraints\Recaptcha3Validator;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Cache\Adapter\FilesystemAdapter;
use Symfony\Component\Cache\CacheItem;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Notifier\ChatterInterface;
use Symfony\Component\Notifier\Message\ChatMessage;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;

#[Route('/reclamation')]
class ReclamationController extends AbstractController
{
    // Index génerale de la reclamation
    #[Route('/', name: 'app_reclamation_index', methods: ['GET'])]
    public function index(SessionInterface $session): Response
    {
        $personne=$session->get('id');
        if( $personne->getRole() == "admin") {
            return $this->redirectToRoute('app_reclamation_admin_index');
        }
        return $this->redirectToRoute('app_reclamation_user_index');
    }
    #[Route('/search', name: 'app_reclamation_search', methods: ['GET','POST'])]
    public function search(Request $request) : JsonResponse{
        $query = $request->get('q');
        $tabIds = $request->get('list');
        $arr = json_decode($tabIds);
        $data = [];
        foreach($arr as $reclam){
            if($reclam->idRec == $query)
                $data[] = $reclam->idRec;
            elseif($reclam->Nom == $query)
                $data[] = $reclam->idRec;
            elseif($reclam->Prenom == $query)
                $data[] = $reclam->idRec;
            elseif($reclam->titre == $query)
                $data[] = $reclam->idRec;
            elseif($query == "cloturé" && $reclam->status == "cloturer")
                $data[] = $reclam->idRec;
            elseif($query == "cloture" && $reclam->status == "cloturer")
                $data[] = $reclam->idRec;
            elseif ($query == "cloturer" && $reclam->status == "cloturer")
                $data[] = $reclam->idRec;
            elseif($query == "ouvert" && $reclam->status == "ouvert")
                $data[] = $reclam->idRec;
            elseif($query == "ouvert" && $reclam->status == "EnAttenteUser")
                $data[] = $reclam->idRec;
            elseif($query == "En Attente" && $reclam->status == "EnAttenteAdmin")
                $data[] = $reclam->idRec;
            elseif($query == $reclam->fullName)
                $data[] = $reclam->idRec;
        }
        return new JsonResponse($data);
    }
    #[Route('/admin/{filter}', name: 'app_reclamation_admin_index', defaults: ['filter' => ''], methods: ['GET'])]
    public function admin_index(ReclamationRepository $reclamationRepository,SessionInterface $session,ImagePersRepository $imagePersRepository,?string $filter = null): Response{

        $personne=$session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        $role = $personne->getRole();
        $form = $this->createForm(ReclamationRechercheDynamiqueType::class);
        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }
        $session->set('last', $last);
        $last=$session->get('last');
        $cache = new FilesystemAdapter();
        $cache->clear();
        $reclamationss = $cache->get('liste_reclam', function (CacheItem $item) use ($reclamationRepository) {
            $item->expiresAfter(60);
            if(!empty($filter)){
                $reclamTab = $reclamationRepository->tri($filter);
                $item->set($reclamTab);
                return $reclamTab;
            } else {
                $reclamTab = $reclamationRepository->findAll();
                $item->set($reclamTab);
                return $reclamTab;
            }
        });
        $nbReclamOuverte = 0;
        $nbReclamEnAtte = 0;
        $nbReclamCloturer = 0;
        foreach ($reclamationss as $reclam)
        {
            if($reclam->getStatus() == "ouvert" OR $reclam->getStatus() == "EnAttenteRepAdmin"){
                $nbReclamOuverte++;
            } elseif($reclam->getStatus() == "cloturer"){
                $nbReclamCloturer++;
            } elseif($reclam->getStatus() == "EnAttenteRepUser"){
                $nbReclamEnAtte++;
            }
        }
        /*$ReclamOuverte = $reclamationRepository->findBy(['status'=>'ouvert']);
        $nbReclamOuverte = count($ReclamOuverte);
        $ReclamOuverte = $reclamationRepository->findBy(['status' => 'EnAttenteRepAdmin']);
        $nbReclamOuverte += count($ReclamOuverte);
        $ReclamCloturer = $reclamationRepository->findBy(['status' => 'cloturer']);
        $nbReclamCloturer = count($ReclamCloturer);
        $ReclamEnAttente = $reclamationRepository->findBy(['status' => 'EnAttenteRepUser']);
        $nbReclamEnAtte = count($ReclamEnAttente);
        $reclamationTab = [];
        */
        /*if(empty($filter)) {
            $reclamationss = $reclamationRepository->findAll();
        } else {
            $reclamationss = $reclamationRepository->tri($filter);
        }*/
        return $this->render('templates_back/reclamation/index.html.twig', [
            'personne' => $personne,
            'last'=> $last,
            'reclamations' => $reclamationss,
            'nbReclamOuverte' => $nbReclamOuverte,
            'nbReclamCloturer' => $nbReclamCloturer,
            'nbReclamEnAttente' => $nbReclamEnAtte,
            'roles' => $role,
            'activeFilter' => $filter,
            'formulaire' => $form->createView()
        ]);
    }
    #[Route('/user', name: 'app_reclamation_user_index', methods: ['GET'])]
    public function user_index(request $request,SessionInterface $session,ImagePersRepository $imagePersRepository,ReclamationRepository $reclamationRepository):Response
    {
        $personne=$session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        $role = $personne->getRole();

        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }
        $session->set('last', $last);
        $last=$session->get('last');
        $reclamationss = $reclamationRepository->findAll();
        return $this->render('templates_front/reclamation/index.html.twig',[
            'personne' => $personne,
            'last'=> $last,
            'reclamations' => $reclamationss,
            'uid' => $idPerss
        ]);
    }
    #[Route('/ajouter', name: 'app_reclamation_ajouter', methods: ['GET', 'POST'])]
    public function ajouter(ChatterInterface $chatter, Recaptcha3Validator $validator,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository,ReclamationRepository $reclamationRepository):Response{
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
        $reclam = new Reclamation();
        $form = $this->createForm(ReclamationAjouterType::class,$reclam,[
            'sender' => $idPerss,
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $score = $validator->getLastResponse()->getScore();
            if($score > 1){
                echo "<script>console.log('test')";
            }
            $reclamationRepository->save($reclam, true);

            $message = (new ChatMessage("La Reclamation #".$reclam->getIdRec()." - ".$reclam->getTitre()."\n a été crée par ".$reclam->getUserid()->getPrenomPers()." ".$reclam->getUserid()->getNomPers()))->transport('telegram');
            $chatter->send($message);
            return $this->redirectToRoute('app_reclamation_user_consulter', ['idRec' => $reclam->getIdRec()], Response::HTTP_SEE_OTHER);

        }
        $view = $form->createView();
        return $this->render('templates_front/reclamation/ajouter.html.twig',[
            'personne' => $personne,
            'last'=> $last,
            'form' => $view
        ]);
    }
    #[Route('/user/consulter/{idRec}', name: 'app_reclamation_user_consulter', methods: ['GET', 'POST'])]
    public function consulterr(Reclamation $idRec,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository, ReponseRepository $repRepo, ReclamationRepository $recRepo): Response
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
        $date = $idRec->getDateheure()->format('d/m/y H:i');
        $reps = $repRepo->findBy(['rec' => $idRec]);
        $idUser = $session->get('id')->getIdPers();
        $Message = new Reponse();
        $form = $this->createForm(ReponseType::class,$Message,[
            'sender' => $idUser,
            'rec' => $idRec->getIdRec(),
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $repRepo->save($Message, true);
            $idRec->setStatus("EnAttenteRepAdmin");
            $recRepo->save($idRec,true);
            return $this->redirectToRoute('app_reclamation_user_consulter', ['idRec' => $idRec->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        $clientImg = $imagePersRepository->findBy(['idPers' => $idRec->getUserid()]);
        $clientImg = array_reverse($clientImg);
        if(!empty($clientImg)){
            $i = $clientImg[0];
            $client = $i->getLast();
        }
        else{
            $client="account (1).png";
        }
        return $this->render('templates_front/reclamation/consulter.html.twig',[
            'personne' => $personne,
            'last'=> $last,
            'reclamation' => $idRec,
            'date' => $date,
            'reps' => $reps,
            'uid' => $idUser,
            'message' => $Message,
            'form' => $view,
            'clientImg' =>$client
        ]);
    }
    #[Route('/admin/consulter/{idRec}', name: 'app_reclamation_admin_consulter', methods: ['GET','POST'])]
    public function consulter(Reclamation $idRec,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository, ReponseRepository $repRepo, ReclamationRepository $recRepo): Response
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
        $date = $idRec->getDateheure()->format('d/m/y H:i');
        $reps = $repRepo->findBy(['rec' => $idRec]);
        $idUser = $session->get('id')->getIdPers();
        $Message = new Reponse();
        $form = $this->createForm(ReponseType::class,$Message,[
            'sender' => $idUser,
            'rec' => $idRec->getIdRec(),
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $repRepo->save($Message, true);
            $idRec->setStatus("EnAttenteRepUser");
            $recRepo->save($idRec,true);
            return $this->redirectToRoute('app_reclamation_admin_consulter', ['idRec' => $idRec->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        $clientImg = $imagePersRepository->findBy(['idPers' => $idRec->getUserid()]);
        $clientImg = array_reverse($clientImg);
        if(!empty($clientImg)){
            $i = $clientImg[0];
            $client = $i->getLast();
        }
        else{
            $client="account (1).png";
        }
        if(!empty($reps)){
            $interval = array();
            $intevall = $reps[0]->getTimestamp()->diff($idRec->getDateheure());
            $interval[] = $intevall;
            for($i = 0; $i < count($reps) - 1; $i++){
                $timestamp1 = $reps[$i]->getTimestamp();
                $timestamp2 = $reps[$i+1]->getTimestamp();
                $inter = $timestamp1->diff($timestamp2);
                $interval[] = $inter;
            }
            $totalIntervals = count($interval);
            $totalIntervalSeconds = 0;
            foreach ($interval as $timeInterval) {
                $totalIntervalSeconds += $timeInterval->s + $timeInterval->i * 60 + $timeInterval->h * 3600 + $timeInterval->days * 86400;
            }
            $averageIntervalSeconds = $totalIntervalSeconds / $totalIntervals;
            $avg = intval($averageIntervalSeconds);
            $averageInterval = \DateInterval::createFromDateString($avg.'seconds');
            $datea = $averageInterval->format('%s s');


        } else {
            $datea = "-1s";
        }
        return $this->render('templates_back/reclamation/new_consulter.html.twig', [
            'personne' => $personne,
            'last'=> $last,
            'reclamation' => $idRec,
            'date' => $date,
            'reps' => $reps,
            'uid' => $idUser,
            'message' => $Message,
            'form' => $view,
            'clientImg' => $client,
            'avg' => $datea
        ]);
    }
    #[Route('/modifier/{idRec}', name: 'app_reclamation_modifier', methods: ['GET', 'POST'])]
    public function modifier(Reclamation $idRec,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository,ReclamationRepository $reclamationRepository,ReponseRepository $repRepo){
        $personne=$session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        $reps = $repRepo->findBy(['rec' => $idRec]);
        if(!empty($images)){
            $i= $images[0];
            $last=$i->getLast();
        }
        else{
            $last="account (1).png";
        }
        $session->set('last', $last);
        $last=$session->get('last');
        $form = $this->createForm(ReclamationType::class, $idRec,['desc' => $idRec->getDescription()]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($idRec, true);
            return $this->redirectToRoute('app_reclamation_admin_consulter', ['idRec' => $idRec->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        return $this->render('templates_back/reclamation/modifier.html.twig', [
            'personne' => $personne,
            'last'=> $last,
            'reclamation' => $idRec,
            'form' => $view,
            'reps' => $reps,
            'uid' => $idPerss
        ]);
    }
    #[Route('/cloturer/{idRec}', name: 'app_reclamation_cloturer', methods: ['POST'])]
    public function cloturer(ChatterInterface $chatter,Request $request, Reclamation $reclamation,SessionInterface $session, ReclamationRepository $reclamationRepository): Response{
        $session->getFlashBag()->clear();
        if ($this->isCsrfTokenValid('cloturer'.$reclamation->getIdRec(), $request->request->get('_token'))) {
            try{
                $reclamation->setStatus("cloturer");
                $reclamationRepository->save($reclamation,true);
                $this->addFlash('success', 'Réclamation a été cloturé avec success');
            } catch (\Exception $e){
                $this->addFlash('error', 'Erreur lors de la cloturation de la reclamation');
            }
            $personne=$session->get('id');
            $message = (new ChatMessage("La Reclamation #".$reclamation->getIdRec()."\n de ".$reclamation->getUserid()->getPrenomPers()." ".$reclamation->getUserid()->getNomPers()."\n a été cloturé par ".$personne->getPrenomPers()." ".$personne->getNomPers()))->transport('telegram');
            $chatter->send($message);

        }
        return $this->redirectToRoute('app_reclamation_admin_index', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/user/cloturer/{idRec}', name: 'app_reclamation_user_cloturer', methods: ['POST'])]
    public function cloturerU(Request $request, Reclamation $reclamation,SessionInterface $session, ReclamationRepository $reclamationRepository): Response{
        $session->getFlashBag()->clear();
        if ($this->isCsrfTokenValid('cloturer'.$reclamation->getIdRec(), $request->request->get('_token'))) {
            try{
                $reclamation->setStatus("cloturer");
                $reclamationRepository->save($reclamation,true);
                $this->addFlash('success', 'Réclamation a été cloturé avec success');
            } catch (\Exception $e){
                $this->addFlash('error', 'Erreur lors de la cloturation de la reclamation');
            }
        }

        return $this->redirectToRoute('app_reclamation_user_consulter', ['idRec' => $reclamation->getIdRec()], Response::HTTP_SEE_OTHER);
    }
    #[Route('/supprimer/{idRec}', name: 'app_reclamation_delete', methods: ['POST'])]
    public function delete(Request $request, Reclamation $reclamation, ReclamationRepository $reclamationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reclamation->getIdRec(), $request->request->get('_token'))) {
            try{
                $reclamationRepository->remove($reclamation, true);
                $this->addFlash('success', 'Réclamation Suprimer avec success');
            } catch (\Exception $e){
                $this->addFlash('error', 'Erreur lors de la suppression de la reclamation');
            }
        }

        return $this->redirectToRoute('app_reclamation_admin_index', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/user/reponse/supprimer/{idRep}', name: 'app_reclamation_user_deleteRep', methods: ['POST'])]
    public function deleteRepU(Request $request, Reponse $rep, ReponseRepository $repRepo): Response
    {
        $idRec = $rep->getRec();
        if ($this->isCsrfTokenValid('delete'.$rep->getIdRep(), $request->request->get('_token'))) {
            $repRepo->remove($rep, true);
        }

        return $this->redirectToRoute('app_reclamation_user_consulter', ['idRec'=> $idRec->getIdRec()], Response::HTTP_SEE_OTHER);
    }
    #[Route('/admin/reponse/supprimer/{idRep}', name: 'app_reclamation_admin_deleteRep', methods: ['POST'])]
    public function deleteRepA(Request $request, Reponse $rep, ReponseRepository $repRepo): Response
    {
        $idRec = $rep->getRec();
        if ($this->isCsrfTokenValid('delete'.$rep->getIdRep(), $request->request->get('_token'))) {
            $repRepo->remove($rep, true);
        }

        return $this->redirectToRoute('app_reclamation_admin_consulter', ['idRec'=> $idRec->getIdRec()], Response::HTTP_SEE_OTHER);
    }
    #[Route('/admin/modifierMessage/{idRep}', name: 'app_reclamation_modifierMessage', methods: ['GET','POST'])]
    public function editM(Reponse $idRep,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository, ReponseRepository $repRepo, ReclamationRepository $recRepo) : Response{
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
        $form = $this->createForm(ReponseModifierType::class, $idRep,[
            'sender' => (string) $idRep->getSenderid()->getIdPers(),
            'rec' => (string) $idRep->getRec()->getIdRec(),
            'mess'=> $idRep->getMessage()
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $repRepo->save($idRep, true);
            return $this->redirectToRoute('app_reclamation_admin_consulter', ['idRec' => $idRep->getRec()->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        return $this->render('templates_back/reclamation/modifierRep.html.twig',[
            'personne' => $personne,
            'last'=> $last,
            'form' => $view,
            'rep' => $idRep
        ]);
    }
    #[Route('/user/modifierMessage/{idRep}', name: 'app_reclamation_modifierMessage_user', methods: ['GET','POST'])]
    public function editMe(Reponse $idRep,request $request,SessionInterface $session,ImagePersRepository $imagePersRepository, ReponseRepository $repRepo, ReclamationRepository $recRepo) : Response{
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
        $form = $this->createForm(ReponseModifierType::class, $idRep,[
            'sender' => (string) $idRep->getSenderid()->getIdPers(),
            'rec' => (string) $idRep->getRec()->getIdRec(),
            'mess'=> $idRep->getMessage()
        ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $repRepo->save($idRep, true);
            return $this->redirectToRoute('app_reclamation_user_consulter', ['idRec' => $idRep->getRec()->getIdRec()], Response::HTTP_SEE_OTHER);
        }
        $view = $form->createView();
        return $this->render('templates_front/reclamation/modifierRep.html.twig',[
            'personne' => $personne,
            'last'=> $last,
            'form' => $view,
            'rep' => $idRep
        ]);
    }


    /// Mobile
    /// Reclamations
    /// Index
    #[Route('/m', name: 'app_reclamations_index_mobile', methods: ['GET', 'POST'])]
    public function indexMobileReclamations(ReclamationRepository $reclamRepo, request $request, SessionInterface $session, SerializerInterface $serializer): Response
    {
        $reclams = $reclamRepo->findAll();
        $json = $serializer->serialize($reclams, 'json', ['groups' => "Reclamations"]);
        return new Response($json);
    }
    #[Route('/m/{uid}', name: 'app_reclamations_index_mobile', methods: ['GET', 'POST'])]
    public function indexMobileReclamationUser(Personne $uid,ReclamationRepository $reclamRepo, request $request, SessionInterface $session, SerializerInterface $serializer): Response
    {
        $reclams = $reclamRepo->findby(['userid'=>$uid]);
        $json = $serializer->serialize($reclams, 'json', ['groups' => "Reclamations"]);
        return new Response($json);
    }
    ///Crud
    #[Route('/m/ajouterRec', name: 'app_reclamations_index_mobile', methods: ['GET', 'POST'])]
    public function AjouterRecMobile(ChatterInterface $chatter,NormalizerInterface $normalizer,PersonneRepository $persRepo,ReclamationRepository $reclamRepo, request $request, SessionInterface $session, SerializerInterface $serializer): Response
    {
        $em = $this->getDoctrine()->getManager();
        $reclam = new Reclamation();
        $p = $persRepo->findOneBy(['idPers'=>$request->get('userId')]);
        $reclam->setUserid($p);
        $reclam->setDescription($request->get('description'));
        $reclam->setTitre($request->get('titre'));
        $reclam->setStatus("ouvert");
        $em->persist($reclam);
        $em->flush();
        $message = (new ChatMessage("La Reclamation #".$reclam->getIdRec()." - ".$reclam->getTitre()."\n a été crée par ".$reclam->getUserid()->getPrenomPers()." ".$reclam->getUserid()->getNomPers()))->transport('telegram');
        $chatter->send($message);
        $json = $normalizer->normalize($reclam,'json',['groups' => 'Reclamations']);
        return new Response(json_encode($json));

    }
    #[Route('/m/{idRec}/consulter', name: 'app_reclamations_index_mobile', methods: ['GET', 'POST'])]
    public function consulterRecMobile(Reclamation $idRec,ReclamationRepository $reclamRepo, request $request, SessionInterface $session, SerializerInterface $serializer): Response
    {
        $reponses = $reclamRepo->findby(['rec'=>$idRec]);
        $json = $serializer->serialize($reponses, 'json', ['groups' => "Reponses"]);
        return new Response($json);
    }
    #[Route('/m/update/{idRec}', name: 'app_reclamations_modifier_mobile', methods: ['GET','POST'])]
    public function modifierRecMobile(PersonneRepository $personneRepository,Reclamation $idRec,ReclamationRepository $reclamRepo, request $request, SessionInterface $session, SerializerInterface $serializer) : Response{
        $em = $this->getDoctrine()->getManager();
        $idRec->setTitre($request->get('titre'));
        $idRec->setStatus($request->get('status'));
        $idRec->setDescription($request->get('description'));
        $idRec->setUserid($personneRepository->findOneBy(['userid' => $request->get('uid')]));
        $em->flush();
        $json = $serializer->serialize($idRec, 'json', ['groups' => "Reclamations"]);
        return new Response($json);
    }
    #[Route('/m/supprimerRec/{idRec}', name: 'app_reclamations_supprimer_mobile', methods: ['GET','POST'])]
    public function supprimerRecMobile(Reclamation $idRec,ReclamationRepository $reclamRepo, request $request, SessionInterface $session, SerializerInterface $serializer) : Response{
        $em = $this->getDoctrine()->getManager();
        $em->remove($idRec);
        $em->flush();
        $json = $serializer->serialize($idRec, 'json', ['groups' => "Reclamations"]);
        return new Response($json);
    }
    #[Route('/m/cloturerRec/{idRec}', name: 'app_reclamations_cloturer_mobile', methods: ['GET','POST'])]
    public function cloturerRecMobile(ChatterInterface$chatter,Reclamation $idRec,ReclamationRepository $reclamRepo, request $request, SessionInterface $session, SerializerInterface $serializer) : Response{
        $em = $this->getDoctrine()->getManager();
        $idRec->setStatus("cloturer");
        $em->flush();
        $json = $serializer->serialize($idRec, 'json', ['groups' => "Reclamations"]);

        $message = (new ChatMessage("La Reclamation #".$idRec->getIdRec()."\n de ".$idRec->getUserid()->getPrenomPers()." ".$idRec->getUserid()->getNomPers()."\n a été cloturé."))->transport('telegram');
        $chatter->send($message);
        return new Response($json);
    }
    ///// Reponses
    #[Route('/m/ajouterRep/{uid}/{idRec}', name: 'app_reclamations_index_mobile', methods: ['GET', 'POST'])]
    public function AjouterRepMobile(Personne $uid,Reclamation $idRec,NormalizerInterface $normalizer,request $request): Response
    {
        $em = $this->getDoctrine()->getManager();
        $reponse = new Reponse();
        $reponse->setRec($idRec);
        $reponse->setSenderid($uid);
        $reponse->setMessage($request->get('message'));
        $em->persist($reponse);
        $em->flush();
        $json = $normalizer->normalize($reponse, 'json', ['groups' => 'Reponses']);
        return new Response(json_encode($json));
    }
    #[Route('/m/updateRep/{idRep}', name: 'app_reclamations_modifier_reponse_mobile', methods: ['GET','POST'])]
    public function modifierRepMobile(Reponse $idRep,request $request,SerializerInterface $serializer) : Response{
        $em = $this->getDoctrine()->getManager();
        $idRep->setMessage($request->get('message'));
        $em->flush();
        $json = $serializer->serialize($idRep, 'json', ['groups' => "Reponses"]);
        return new Response($json);
    }
    #[Route('/m/removeRep/{idRep}', name: 'app_reclamations_supprimer_reponse_mobile', methods: ['GET','POST'])]
    public function supprimerRepMobile(Reponse $idRep,SerializerInterface $serializer) : Response{
        $em = $this->getDoctrine()->getManager();
        $em->remove($idRep);
        $em->flush();
        $json = $serializer->serialize($idRep, 'json', ['groups' => "Reponses"]);
        return new Response($json);
    }
}


