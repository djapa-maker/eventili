<?php

namespace App\Repository;
//---------------------------------------------------------------------------------------
use App\Entity\Sousservice;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Query\Expr\Join;
use App\Entity\Imagess;
//---------------------------------------------------------------------------------------
/**
 * @extends ServiceEntityRepository<Sousservice>
 *
 * @method Sousservice|null find($id, $lockMode = null, $lockVersion = null)
 * @method Sousservice|null findOneBy(array $criteria, array $orderBy = null)
 * @method Sousservice[]    findAll()
 * @method Sousservice[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
//---------------------------------------------------------------------------------------
class SousserviceRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Sousservice::class);
    }
//-----------------------------------------------------------------------------
    public function save(Sousservice $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
//-----------------------------------------------------------------------------
    public function remove(Sousservice $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
//-----------------------------------------------------------------------------  
    /**
     * @return Sousservice 
     */
    public function findOneByName($value): array
    {
        return $this->createQueryBuilder('s')
            ->where('s.nom like :n')
            ->setParameter('n', '%' . $value . '%')
            ->getQuery()
            ->getResult();
    }
//-----------------------------------------------------------------------------
    /**
     * @return Sousservice[] Returns an array of Service objects
     */
    public function findListByNames($value): array
    {
        return $this->createQueryBuilder('s')
            ->where('s.nom like :n')
            ->setParameter('n', '%' . $value . '%')
            ->getQuery()
            ->getResult();
    }
//----------------------------------------------------------------------------- 
    /**
     * @return Sousservice[] Returns an array of Service objects
     */
    public function findSSByServiceIdAndName($value, $value1): array
    {
        return $this->createQueryBuilder('s')
            ->where('s.idService = :n')
            ->andWhere('s.nom like :z')
            ->setParameter('n', $value)
            ->setParameter('z', '%' . $value1 . '%')
            ->getQuery()
            ->getResult();
    }
//-----------------------------------------------------------------------------

    /**
     * @return Sousservice[] Returns an array of Service objects
     */

    public function getAllByServiceName($name): array
    {
        return $this->createQueryBuilder('s')
            ->join('s.idService', 'c')
            ->addSelect('c')
            ->andWhere('c.nom = :name')
            ->setParameter('name', $name)
            ->getQuery()
            ->getResult();
    }
//---------------------------------------------------------------------------------------
    public function getSousserviceWithImagess(EntityManager $entityManager, $id)
    {
        $queryBuilder = $entityManager->createQueryBuilder();
        $queryBuilder->select('sousservice', 'imagess')
            ->from('App\Entity\Sousservice', 'sousservice')
            ->leftJoin(
                'App\Entity\Imagess',
                'imagess',
                Join::WITH,
                $queryBuilder->expr()->eq('sousservice', 'imagess.sousService')
            )
            ->where('sousservice.id = :id')
            ->setParameter('id', $id);

        $subquery = $entityManager->createQueryBuilder();
        $subquery->select('im')
            ->from('App\Entity\Imagess', 'im')
            ->where('im.sousService = sousservice.id')
            ->getDQL();

        $queryBuilder->andWhere($queryBuilder->expr()->in('imagess.id', $subquery->getDQL()));

        $query = $queryBuilder->getQuery();
        $result = $query->getResult();
    }
}
