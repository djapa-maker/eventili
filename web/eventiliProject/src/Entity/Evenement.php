<?php

namespace App\Entity;

use DateTime;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\EvenementRepository;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: EvenementRepository::class)]
class Evenement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idEv = null;


    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le titre')]
    private ?String $titre =null;


    #[ORM\Column]
    private ?DateTime $dateDebut = null;


    #[ORM\Column]
    private ?DateTime $dateFin= null;


    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le nom')]
    private ?String $descriptionEv = null;


    #[ORM\Column]
    private ?String $type=null;


    #[ORM\Column]
    private ?String $visibilite = null;


    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le nom')]
    private ?int $limiteparticipant =null;


    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le prix')]
    private ?float $prix = null;


    #[ORM\ManyToOne(targetEntity: CategEvent::class)]
    #[ORM\JoinColumn(name: "id_categ", referencedColumnName: "id_categ")]
    private ?CategEvent $idCateg;



    #[ORM\ManyToOne(targetEntity: Personne::class)]
    #[ORM\JoinColumn(name: "id_pers", referencedColumnName: "id_pers")]
    private ?Personne $idPers=null;

    public function getIdEv(): ?int
    {
        return $this->idEv;
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

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->dateDebut;
    }

    public function setDateDebut(\DateTimeInterface $dateDebut): self
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->dateFin;
    }

    public function setDateFin(\DateTimeInterface $dateFin): self
    {
        $this->dateFin = $dateFin;

        return $this;
    }

    public function getDescriptionEv(): ?string
    {
        return $this->descriptionEv;
    }

    public function setDescriptionEv(string $descriptionEv): self
    {
        $this->descriptionEv = $descriptionEv;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

        return $this;
    }

    public function getVisibilite(): ?string
    {
        return $this->visibilite;
    }

    public function setVisibilite(string $visibilite): self
    {
        $this->visibilite = $visibilite;

        return $this;
    }

    public function getLimiteparticipant(): ?int
    {
        return $this->limiteparticipant;
    }

    public function setLimiteparticipant(int $limiteparticipant): self
    {
        $this->limiteparticipant = $limiteparticipant;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getIdCateg(): ?CategEvent
    {
        return $this->idCateg;
    }

    public function setIdCateg(?CategEvent $idCateg): self
    {
        $this->idCateg = $idCateg;

        return $this;
    }

    public function getIdPers(): ?Personne
    {
        return $this->idPers;
    }

    public function setIdPers(?Personne $idPers): self
    {
        $this->idPers = $idPers;

        return $this;
    }


}
