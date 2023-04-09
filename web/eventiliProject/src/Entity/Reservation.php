<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\reservationRepository;


#[ORM\Entity(repositoryClass: reservationRepository::class)]
class Reservation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idRes=null;


    #[ORM\Column(type: 'text', nullable: true)]
    private ?String $idSs = null;


    #[ORM\Column(type: 'boolean')]
    private ?bool $status = null;


    #[ORM\ManyToOne(targetEntity: Evenement::class)]
    #[ORM\JoinColumn(name: "id_ev", referencedColumnName: "id_ev")]
    private ?Evenement $idEv=null;

    public function getIdRes(): ?int
    {
        return $this->idRes;
    }

    public function getIdSs(): ?string
    {
        return $this->idSs;
    }

    public function setIdSs(?string $idSs): self
    {
        $this->idSs = $idSs;

        return $this;
    }

    public function isStatus(): ?bool
    {
        return $this->status;
    }

    public function setStatus(bool $status): self
    {
        $this->status = $status;

        return $this;
    }

    public function getIdEv(): ?Evenement
    {
        return $this->idEv;
    }

    public function setIdEv(?Evenement $idEv): self
    {
        $this->idEv = $idEv;

        return $this;
    }


}
