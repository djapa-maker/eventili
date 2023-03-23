<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Sponsoring
 *
 * @ORM\Table(name="sponsoring", indexes={@ORM\Index(name="fk_sponso", columns={"id_trans"}), @ORM\Index(name="fk_eve", columns={"id_event"})})
 * @ORM\Entity
 */
class Sponsoring
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_sponso", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idSponso;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_debut", type="date", nullable=false)
     */
    private $dateDebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_fin", type="date", nullable=false)
     */
    private $dateFin;

    /**
     * @var int
     *
     * @ORM\Column(name="nombre_impression", type="integer", nullable=false)
     */
    private $nombreImpression;

    /**
     * @var int
     *
     * @ORM\Column(name="id_trans", type="integer", nullable=false)
     */
    private $idTrans;

    /**
     * @var \Evenement
     *
     * @ORM\ManyToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_event", referencedColumnName="id_ev")
     * })
     */
    private $idEvent;

    public function getIdSponso(): ?int
    {
        return $this->idSponso;
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

    public function getNombreImpression(): ?int
    {
        return $this->nombreImpression;
    }

    public function setNombreImpression(int $nombreImpression): self
    {
        $this->nombreImpression = $nombreImpression;

        return $this;
    }

    public function getIdTrans(): ?int
    {
        return $this->idTrans;
    }

    public function setIdTrans(int $idTrans): self
    {
        $this->idTrans = $idTrans;

        return $this;
    }

    public function getIdEvent(): ?Evenement
    {
        return $this->idEvent;
    }

    public function setIdEvent(?Evenement $idEvent): self
    {
        $this->idEvent = $idEvent;

        return $this;
    }


}
