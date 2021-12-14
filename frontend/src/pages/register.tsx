import * as yup from "yup";
import {FC, useState} from "react";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import {useNavigate} from "react-router-dom";
import {registerUser} from "../api/auth";
import {toast} from "react-toastify";
import Container from "@mui/material/Container";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {Alert, Collapse, IconButton} from "@mui/material";
import Grid from "@mui/material/Grid";
import Link from "@mui/material/Link";
import {Copyright} from "../components/copyright";

interface IFormInputs {
  email: string;
  password: string;
  confirmPassword: string;
  name: string;
  surname: string;
}

const schema = yup.object().shape({
  email: yup.string().email().required("E-mail address is required"),
  password: yup.string().min(4).max(20).required(),
  confirmPassword: yup.string().oneOf([yup.ref('password'), null], 'Passwords must match'),
  name: yup.string().required("Name is required"),
  surname: yup.string().required("Surname is required"),
});

const Register: FC = () => {

  const {
    handleSubmit,
    control,
    formState: {errors},
  } = useForm<IFormInputs>({resolver: yupResolver(schema)})

  const navigate = useNavigate();

  const [failedLogin, setFailedLogin] = useState(false);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    console.log('login data: ', data);
    setFailedLogin(false);
    registerUser(data)
      .then(function (response) {
        if (response.status === 200)
          toast.success('Signed up successfully.', {
            position: "top-right",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        navigate('/login')
      })
      .catch(err => {
        toast.error('Failed to sign up.', {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        setFailedLogin(true)
      });
  }

  return (
    <Container component="main" sx={{
      border: '1px solid #d6daff',
      boxShadow: '5px 5px 12px #d6daff'
    }} maxWidth="xs">
      <CssBaseline enableColorScheme={true}/>
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
          <LockOutlinedIcon/>
        </Avatar>
        <Typography component="h5" variant="h4">
          Create new account
        </Typography>
        <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate
             sx={{mt: 1}}>
          <Controller
            name='email'
            control={control}
            defaultValue='mail@mail.com'
            render={({field}) => (
              <TextField
                {...field}
                label="E-mail address"
                variant="outlined"
                error={!!errors.email}
                helperText={errors.email ? errors.email?.message : ''}
                margin="normal"
                required
                fullWidth
                id="email"
                name="email"
                autoComplete="email"
                autoFocus
              />
            )}
          />

          <Controller
            name='password'
            control={control}
            defaultValue=''
            render={({field}) => (
              <TextField
                {...field}
                variant="outlined"
                error={!!errors.password}
                helperText={errors.password ? errors.password?.message : ''}
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
              />
            )}
          />

          <Controller
            name='confirmPassword'
            control={control}
            defaultValue=''
            render={({field}) => (
              <TextField
                {...field}
                variant="outlined"
                error={!!errors.confirmPassword}
                helperText={errors.confirmPassword ? errors.confirmPassword?.message : ''}
                margin="normal"
                required
                fullWidth
                name="confirmPassword"
                label="Confirm Password"
                type="confirmPassword"
                id="confirmPassword"
                autoComplete="current-password"
              />
            )}
          />

          <Controller
            name='name'
            control={control}
            defaultValue=''
            render={({field}) => (
              <TextField
                {...field}
                variant="outlined"
                error={!!errors.name}
                helperText={errors.name ? errors.name?.message : ''}
                margin="normal"
                required
                fullWidth
                name="name"
                label="Name"
                type="name"
                id="name"
              />
            )}
          />

          <Controller
            name='surname'
            control={control}
            defaultValue=''
            render={({field}) => (
              <TextField
                {...field}
                variant="outlined"
                error={!!errors.surname}
                helperText={errors.surname ? errors.surname?.message : ''}
                margin="normal"
                required
                fullWidth
                name="surname"
                label="Surname"
                type="surname"
                id="surname"
              />
            )}
          />

          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{mt: 3, mb: 2}}
          >
            Sign up
          </Button>
          <Collapse in={failedLogin}>
            <Alert
              severity="error"
              action={
                <IconButton
                  aria-label="close"
                  color="inherit"
                  size="small"
                  onClick={() => {
                    setFailedLogin(false);
                  }}
                >
                  <LockOutlinedIcon fontSize="inherit"/>
                </IconButton>
              }
            >
              Invalid e-mail address or password
            </Alert>
          </Collapse>
          <Grid container>
            <Grid item>
              <Link href="login" variant="body2">
                {"Already have an account? Sign In"}
              </Link>
            </Grid>
          </Grid>
        </Box>
      </Box>
      <Copyright/>
    </Container>
  );
}

export default Register;