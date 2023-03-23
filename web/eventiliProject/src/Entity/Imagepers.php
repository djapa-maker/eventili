<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Imagepers
 *
 * @ORM\Table(name="imagepers", indexes={@ORM\Index(name="fk_perI", columns={"id_pers"})})
 * @ORM\Entity
 */
class Imagepers
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_imp", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idImp;

    /**
     * @var string
     *
     * @ORM\Column(name="imageP", type="string", length=255, nullable=false)
     */
    private $imagep;

    /**
     * @var string
     *
     * @ORM\Column(name="last", type="string", length=255, nullable=false)
     */
    private $last;

    /**
     * @var \Personne
     *
     * @ORM\ManyToOne(targetEntity="Personne")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_pers", referencedColumnName="id_pers")
     * })
     */
    private $idPers;

    public function getIdImp(): ?int
    {
        return $this->idImp;
    }

    public function getImagep(): ?string
    {
        return $this->imagep;
    }

    public function setImagep(string $imagep): self
    {
        $this->imagep = $imagep;

        return $this;
    }

    public function getLast(): ?string
    {
        return $this->last;
    }

    public function setLast(string $last): self
    {
        $this->last = $last;

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
