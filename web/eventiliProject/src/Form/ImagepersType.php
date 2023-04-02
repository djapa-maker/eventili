<?php

namespace App\Form;
use Symfony\Component\HttpFoundation\File\File;
use App\Entity\Imagepers;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\Personne;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\DataTransformerInterface;
use Symfony\Component\Form\Exception\TransformationFailedException;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\OptionsResolver\OptionsResolver;

class StringToFileTransformer implements DataTransformerInterface
{
    private $fileDirectory;

    public function __construct(string $fileDirectory)
    {
        $this->fileDirectory = $fileDirectory;
    }

    public function transform($value)
    {
        // transform the file object to a string for rendering in the form field
        if ($value instanceof File) {
            return $value->getFilename();
        }

        return $value;
    }

    public function reverseTransform($value)
    {
        // transform the uploaded file to a File object
        if ($value instanceof UploadedFile) {
            $filename = md5(uniqid()) . '.' . $value->getClientOriginalExtension();
            $value->move($this->fileDirectory, $filename);

            return new File($this->fileDirectory . '/' . $filename);
        }

        // transform the existing file path to a File object
        if (is_string($value)) {
            return new File($this->fileDirectory . '/' . $value);
        }

        throw new TransformationFailedException('Expected a string or an instance of UploadedFile');
    }
}
class ImagepersType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
           ->add('imagep')
            ->add('last')
           ->add('idPers',EntityType::class,['class'=> Personne::class,'choice_label'=>'idPers','multiple'=>false,'expanded'=>false])
           
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Imagepers::class,
        ]);
    }
}
