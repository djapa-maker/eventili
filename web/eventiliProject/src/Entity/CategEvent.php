<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\CategEventRepository; 
#[ORM\Entity(repositoryClass:CategEventRepository::class)]
class CategEvent
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'id_categ')]
    private ?int $idCateg=null;

    #[ORM\Column]
    private ?String $type=null;

    public function getIdCateg(): ?int
    {
        return $this->idCateg;
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


}
