<?php

namespace App\Entity;

use App\Entity\Transaction;
use App\Repository\SponsoringRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: SponsoringRepository::class)]
class Sponsoring
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'id_sponso')]  
    private ?int $id_sponso  = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $date_debut = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $date_fin = null;

    #[ORM\Column]
    private ?int $nombre_impression = null;

 
    #[ORM\ManyToOne(targetEntity: Transaction::class)]
    #[ORM\JoinColumn(name: 'id_trans', referencedColumnName: 'id_trans', nullable: false)]
    private ?Transaction $id_trans = null;

    #[ORM\ManyToOne(targetEntity: Evenement::class)]
    #[ORM\JoinColumn(name: 'id_event', referencedColumnName: 'id_ev', nullable: false)] 
    private ?Evenement $id_event = null;

 

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->date_debut;
    }

    public function setDateDebut(\DateTimeInterface $date_debut): self
    {
        $this->date_debut = $date_debut;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->date_fin;
    }

    public function setDateFin(\DateTimeInterface $date_fin): self
    {
        $this->date_fin = $date_fin;

        return $this;
    }

    public function getNombreImpression(): ?int
    {
        return $this->nombre_impression;
    }

    public function setNombreImpression(int $nombre_impression): self
    {
        $this->nombre_impression = $nombre_impression;

        return $this;
    }
 
 

    public function getIdEvent(): ?Evenement
    {
        return $this->id_event;
    }

    public function setIdEvent(Evenement $id_event): self
    {
        $this->id_event = $id_event;

        return $this;
    }

 
 

	/**
	 * @return Transaction|null
	 */
	public function getId_trans(): ?Transaction {
         		return $this->id_trans;
         	}
	
	/**
	 * @param Transaction|null $id_trans 
	 * @return self
	 */
	public function setId_trans(? Transaction $id_trans): self {
         		$this->id_trans = $id_trans;
         		return $this;
         	}

 
    public function getIdSponso(): ?int
{
    return $this->id_sponso;
}


    public function getIdTrans(): ?Transaction
    {
        return $this->id_trans;
    }

    public function setIdTrans(?Transaction $id_trans): self
    {
        $this->id_trans = $id_trans;

        return $this;
    }
}
