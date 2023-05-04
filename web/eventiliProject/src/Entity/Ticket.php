<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Ticket
 *
 * @ORM\Table(name="ticket", indexes={@ORM\Index(name="fk_ev", columns={"idEvent"}), @ORM\Index(name="fk_tick", columns={"id_tran"})})
 * @ORM\Entity
 */
class Ticket
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_tick", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idTick;

    /**
     * @var int
     *
     * @ORM\Column(name="nb_tick", type="integer", nullable=false)
     */
    private $nbTick;

    /**
     * @var float
     *
     * @ORM\Column(name="prix_tick", type="float", precision=10, scale=0, nullable=false)
     */
    private $prixTick;

    /**
     * @var string
     *
     * @ORM\Column(name="status", type="string", length=30, nullable=false)
     */
    private $status;

    /**
     * @var \Evenement
     *
     * @ORM\ManyToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idEvent", referencedColumnName="id_ev")
     * })
     */
    private $idevent;

    /**
     * @var \Transaction
     *
     * @ORM\ManyToOne(targetEntity="Transaction")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_tran", referencedColumnName="id_trans")
     * })
     */
    private $idTran;

    public function getIdTick(): ?int
    {
        return $this->idTick;
    }

    public function getNbTick(): ?int
    {
        return $this->nbTick;
    }

    public function setNbTick(int $nbTick): self
    {
        $this->nbTick = $nbTick;

        return $this;
    }

    public function getPrixTick(): ?float
    {
        return $this->prixTick;
    }

    public function setPrixTick(float $prixTick): self
    {
        $this->prixTick = $prixTick;

        return $this;
    }

    public function getStatus(): ?string
    {
        return $this->status;
    }

    public function setStatus(string $status): self
    {
        $this->status = $status;

        return $this;
    }

    public function getIdevent(): ?Evenement
    {
        return $this->idevent;
    }

    public function setIdevent(?Evenement $idevent): self
    {
        $this->idevent = $idevent;

        return $this;
    }

    public function getIdTran(): ?Transaction
    {
        return $this->idTran;
    }

    public function setIdTran(?Transaction $idTran): self
    {
        $this->idTran = $idTran;

        return $this;
    }


}
