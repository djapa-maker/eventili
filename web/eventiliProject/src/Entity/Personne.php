<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\PersonneRepository;
use phpDocumentor\Reflection\Types\Self_;
use PhpParser\Node\Name;
use Symfony\Component\Mime\Message;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: PersonneRepository::class)]
class Personne
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'id_pers')]
    private ?int $idPers=null;

    #[ORM\Column]
    #[Assert\Length(min: 1, max: 255)]
    #[Assert\Regex(
        pattern: "/^[^0-9]*$/",
       message: "Le nom ne doit pas contenir de chiffre"
    )]  
    private ?String $nomPers=null;

    #[ORM\Column]
    #[Assert\Regex(
        pattern: "/^[^0-9]*$/",
       message: "Le prénom ne doit pas contenir de chiffre"
    )]  
    private ?String $prenomPers=null;
   
    #[ORM\Column]
    #[Assert\Length(min: 8, max: 8 , minMessage:"le numéro doit contenir 8 chiffres")]
    #[Assert\Regex(
        pattern: "/^[^a-zA-Z]+$/",
       message: "Le numéro ne doit pas contenir de lettres"
    )]  
    private ?String $numTel=null;

    #[ORM\Column]
    #[Assert\Email(message: "L'adresse email '{{ value }}' n'est pas valide.")]
    private ?String $email=null;

    #[ORM\Column]
    #[Assert\Length(min: 8, max: 255)]
    #[Assert\Regex(
        pattern: '/^(?=.*[a-zA-Z])(?=.*\d).+$/',
        message: "Le mot de passe doit contenir au moins une lettre et un chiffre."
    )]
    private ?String $mdp=null;

    #[ORM\Column]
    private ?String $adresse=null;

    #[ORM\Column]
    #[Assert\Length(min: 20, max: 20)]
    #[Assert\Regex(
        pattern: "/^[^a-zA-Z]+$/",
       message: "Le rib ne doit pas contenir de lettres"
    )] 
    private ?String $rib=null;

    #[ORM\Column]
    private ?String $role=null;

    #[ORM\Column]
    private ?String $token="";

    public function getIdPers(): ?int
    {
        return $this->idPers;
    }
    public function getNomPers(): ?string
    {
        return $this->nomPers;
    }

    public function setNomPers(string $nomPers): self
    {
        $this->nomPers = $nomPers;

        return $this;
    }

    public function getPrenomPers(): ?string
    {
        return $this->prenomPers;
    }

    public function setPrenomPers(string $prenomPers): self
    {
        $this->prenomPers = $prenomPers;

        return $this;
    }

    public function getNumTel(): ?string
    {
        return $this->numTel;
    }

    public function setNumTel(string $numTel): self
    {
        $this->numTel = $numTel;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getMdp(): ?string
    {
        return $this->mdp;
    }

    public function setMdp(string $mdp): self
    {
        $this->mdp = $mdp;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getRib(): ?string
    {
        return $this->rib;
    }

    public function setRib(string $rib): self
    {
        $this->rib = $rib;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function setRole(string $role): self
    {
        $this->role = $role;

        return $this;
    }

    public function getToken(): ?string
    {
        return $this->token;
    }

    public function setToken(string $token): self
    {
        $this->token = $token;

        return $this;
    }
    public function __toString()
    {
        return (string) $this->idPers;
    }
}



