<?php

namespace App\Entity;

use App\Repository\PersonneRepository;
use Doctrine\DBAL\Types\DateTimeType;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\ReclamationRepository;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Bridge\Doctrine\ManagerRegistry;
use Symfony\Component\Validator\Constraints\DateTime;

#[ORM\Entity(repositoryClass: ReclamationRepository::class)]

class Reclamation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private $idRec;

    #[ORM\Column(length: 255)]
    private $description;

    #[ORM\Column(length: 255)]
    private $titre;

    #[ORM\Column(length: 255)]
    private $status;

    #[ORM\Column(type: 'datetime')]
    private $dateheure = 'CURRENT_TIMESTAMP';

    #[ORM\ManyToOne(targetEntity: Personne::class)]
    #[ORM\JoinColumn(name: "userId",referencedColumnName: "id_pers")]
    private $userid;

    public function getIdRec(): ?int
    {
        return $this->idRec;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }
    public function setStatus(string $status): self{
        $this->status = $status;
        return $this;
    }
    public function getStatus(){
        return $this->status;
    }
    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
    }

    public function getDateheure(): \DateTime
    {
        return $this->dateheure;
    }

    public function setDateheure(\DateTimeInterface $dateheure): self
    {
        $this->dateheure = $dateheure;

        return $this;
    }

    public function getUserid(): Personne
    {
        return $this->userid;
    }

    public function setUserid(?Personne $userid): self
    {
        $this->userid = $userid;

        return $this;
    }

}
