<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Evenement
 *
 * @ORM\Table(name="evenement", indexes={@ORM\Index(name="fk_categ", columns={"id_categ"}), @ORM\Index(name="fk_p", columns={"id_pers"})})
 * @ORM\Entity
 */
class Evenement
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_ev", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idEv;

    /**
     * @var string
     *
     * @ORM\Column(name="titre", type="string", length=30, nullable=false)
     */
    private $titre;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_debut", type="datetime", nullable=false)
     */
    private $dateDebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_fin", type="datetime", nullable=false)
     */
    private $dateFin;

    /**
     * @var string
     *
     * @ORM\Column(name="description_ev", type="text", length=65535, nullable=false)
     */
    private $descriptionEv;

    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", length=0, nullable=false)
     */
    private $type;

    /**
     * @var string
     *
     * @ORM\Column(name="visibilite", type="string", length=0, nullable=false)
     */
    private $visibilite;

    /**
     * @var int
     *
     * @ORM\Column(name="limiteParticipant", type="integer", nullable=false)
     */
    private $limiteparticipant;

    /**
     * @var float
     *
     * @ORM\Column(name="prix", type="float", precision=10, scale=0, nullable=false)
     */
    private $prix;

    /**
     * @var \CategEvent
     *
     * @ORM\ManyToOne(targetEntity="CategEvent")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_categ", referencedColumnName="id_categ")
     * })
     */
    private $idCateg;

    /**
     * @var \Personne
     *
     * @ORM\ManyToOne(targetEntity="Personne")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_pers", referencedColumnName="id_pers")
     * })
     */
    private $idPers;

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
