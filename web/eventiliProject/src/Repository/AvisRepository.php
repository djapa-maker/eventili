<?php

namespace App\Repository;
//---------------------------------------------------------------------------------------
use App\Entity\Avis;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
//---------------------------------------------------------------------------------------
/**
 * @extends ServiceEntityRepository<Avis>
 *
 * @method Avis|null find($id, $lockMode = null, $lockVersion = null)
 * @method Avis|null findOneBy(array $criteria, array $orderBy = null)
 * @method Avis[]    findAll()
 * @method Avis[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class AvisRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Avis::class);
    }
//------------------------------------------------------------------------------------------------- 
    public function save(Avis $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
//------------------------------------------------------------------------------------------------- 
    public function remove(Avis $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
//------------------------------------------------------------------------------------------------- 
    public function getAvisByPersonName($name)
{
    $qb = $this->createQueryBuilder('a');
    
    $qb->join('a.pers', 'p')
       ->addSelect('p')
       ->andWhere($qb->expr()->like('p.prenomPers', ':name'))
       ->setParameter('name', '%'.$name.'%');
    
    return $qb->getQuery()->getResult();
}
//---------------------------------------------------------------------------------------
}
