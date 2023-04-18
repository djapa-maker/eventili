<?php

namespace App\Repository;

use App\Entity\Personne;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Exception;
use PhpParser\Node\Expr\Cast\Bool_;

/**
 * @extends ServiceEntityRepository<Personne>
 *
 * @method Personne|null find($id, $lockMode = null, $lockVersion = null)
 * @method Personne|null findOneBy(array $criteria, array $orderBy = null)
 * @method Personne[]    findAll()
 * @method Personne[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class PersonneRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Personne::class);
    }

    public function save(Personne $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
 public function update(Personne $entity, bool $flush = false): void
    {
        $unitOfWork = $this->getEntityManager()->getUnitOfWork();
        $unitOfWork->merge($entity);
    
        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    public function modifyPersonne(int $idPers, Personne $personne): void
{
    $entityManager = $this->getEntityManager();
    $existingPersonne = $entityManager->getRepository(Personne::class)->find($idPers);

    if (!$existingPersonne) {
        throw new Exception('La personne n\'existe pas');
    }

    $existingPersonne->setNomPers($personne->getNomPers());
    $existingPersonne->setPrenomPers($personne->getPrenomPers());
    $existingPersonne->setNumTel($personne->getNumTel());
    $existingPersonne->setEmail($personne->getEmail());
    $existingPersonne->setMdp($personne->getMdp());
    $existingPersonne->setAdresse($personne->getAdresse());
    $existingPersonne->setRib($personne->getRib());

    $entityManager->flush();
}

    public function findemm($email, $mdp): ?Personne
    {
        $personne = $this->createQueryBuilder('p')
            ->andWhere('p.email = :email')
            ->setParameter('email', $email)
            ->getQuery()
            ->getOneOrNullResult();
    
        if (!$personne) {
            return null;
        }
    
        // Vérifier si le mot de passe est correct
        if ($mdp !== $personne->getmdp()) {
            return null;
        }
    
        return $personne;
    }
    /**
    * @return Personne 
    */
    public function findOneByName($value): array
    {
        return $this->createQueryBuilder('p')
            ->where('p.nomPers like :n')
            ->setParameter('n', '%'.$value.'%')
            ->getQuery()
            ->getResult()
        ;
    }
    /**
    * @return Sousservice[] Returns an array of Service objects
    */
    
    public function getAllByPersonneRole($role):array
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.role = :role')
            ->setParameter('role', $role)
            ->getQuery()
            ->getResult();
    }
    public function findd($idPers): ?Personne
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.idPers = :idPers')
            ->setParameter('idPers', $idPers)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    public function remove(Personne $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
}
