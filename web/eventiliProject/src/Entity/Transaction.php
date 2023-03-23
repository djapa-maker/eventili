<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Transaction
 *
 * @ORM\Table(name="transaction", indexes={@ORM\Index(name="fk_trans", columns={"userID"})})
 * @ORM\Entity
 */
class Transaction
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_trans", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idTrans;

    /**
     * @var float
     *
     * @ORM\Column(name="valeur_trans", type="float", precision=10, scale=0, nullable=false)
     */
    private $valeurTrans;

    /**
     * @var string
     *
     * @ORM\Column(name="devis", type="string", length=0, nullable=false)
     */
    private $devis;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_trans", type="date", nullable=false)
     */
    private $dateTrans;

    /**
     * @var string
     *
     * @ORM\Column(name="mode_trans", type="string", length=0, nullable=false)
     */
    private $modeTrans;

    /**
     * @var float
     *
     * @ORM\Column(name="montant_tot", type="float", precision=10, scale=0, nullable=false)
     */
    private $montantTot;

    /**
     * @var \Personne
     *
     * @ORM\ManyToOne(targetEntity="Personne")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="userID", referencedColumnName="id_pers")
     * })
     */
    private $userid;

    public function getIdTrans(): ?int
    {
        return $this->idTrans;
    }

    public function getValeurTrans(): ?float
    {
        return $this->valeurTrans;
    }

    public function setValeurTrans(float $valeurTrans): self
    {
        $this->valeurTrans = $valeurTrans;

        return $this;
    }

    public function getDevis(): ?string
    {
        return $this->devis;
    }

    public function setDevis(string $devis): self
    {
        $this->devis = $devis;

        return $this;
    }

    public function getDateTrans(): ?\DateTimeInterface
    {
        return $this->dateTrans;
    }

    public function setDateTrans(\DateTimeInterface $dateTrans): self
    {
        $this->dateTrans = $dateTrans;

        return $this;
    }

    public function getModeTrans(): ?string
    {
        return $this->modeTrans;
    }

    public function setModeTrans(string $modeTrans): self
    {
        $this->modeTrans = $modeTrans;

        return $this;
    }

    public function getMontantTot(): ?float
    {
        return $this->montantTot;
    }

    public function setMontantTot(float $montantTot): self
    {
        $this->montantTot = $montantTot;

        return $this;
    }

    public function getUserid(): ?Personne
    {
        return $this->userid;
    }

    public function setUserid(?Personne $userid): self
    {
        $this->userid = $userid;

        return $this;
    }


}
