<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Imgev
 *
 * @ORM\Table(name="imgev", indexes={@ORM\Index(name="fk_even", columns={"id_even"})})
 * @ORM\Entity
 */
class Imgev
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_imgev", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idImgev;

    /**
     * @var string
     *
     * @ORM\Column(name="imageE", type="string", length=255, nullable=false)
     */
    private $imagee;

    /**
     * @var \Evenement
     *
     * @ORM\ManyToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_even", referencedColumnName="id_ev")
     * })
     */
    private $idEven;

    public function getIdImgev(): ?int
    {
        return $this->idImgev;
    }

    public function getImagee(): ?string
    {
        return $this->imagee;
    }

    public function setImagee(string $imagee): self
    {
        $this->imagee = $imagee;

        return $this;
    }

    public function getIdEven(): ?Evenement
    {
        return $this->idEven;
    }

    public function setIdEven(?Evenement $idEven): self
    {
        $this->idEven = $idEven;

        return $this;
    }


}
